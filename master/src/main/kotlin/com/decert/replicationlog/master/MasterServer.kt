package com.decert.replicationlog.master

import com.decert.replicationlog.master.config.ServerConfig
import com.decert.replicationlog.master.repository.MessageRepository
import com.decert.replicationlog.master.secondary.SecondaryService
import com.decert.replicationlog.master.secondary.ServiceAddress
import com.decert.replicationlog.master.secondary.ServiceParams
import com.decert.replicationlog.master.secondary.impl.SecondaryServiceImpl
import com.decert.replicationlog.model.message.Message
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.logging.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.atomic.AtomicInteger

class MasterServer(
    serverConfig: ServerConfig,
    private val logger: Logger,
    private val messageRepository: MessageRepository
) {

    private val messageId = AtomicInteger()
    private val secondaryServices: List<SecondaryService> = serverConfig.secondaryConfig.addresses
        .map { address ->
            logger.debug("Replication Instance: ${address.host}:${address.port}")

            SecondaryServiceImpl(
                ServiceParams(
                    ServiceAddress(address.host, address.port),
                    ServerConfig.SECONDARY_TIMEOUT
                )
            )
        }

    suspend fun handlePostMessageCall(call: ApplicationCall) {
        try {
            handlePostMessage(call.receive())
            call.respond(HttpStatusCode.OK, mapOf("status" to "success"))
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.NotAcceptable,
                mapOf("status" to "failure", "reason" to "Invalid message format")
            )
        }
    }

    suspend fun handleGetMessagesCall(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, mapOf("messages" to messageRepository.getMessages()))
    }

    private suspend fun handlePostMessage(message: Message): Int {
        ensureConnectedToSecondaries()

        val id = messageId.incrementAndGet()

        return try {
            sendMessageToSecondaries(id, message)
            messageRepository.addMessage(id, message)

            id
        } catch (e: Exception) {
            messageId.decrementAndGet()
            throw e
        }
    }

    private suspend fun sendMessageToSecondaries(id: Int, message: Message) = coroutineScope {
        secondaryServices
            .map { secondary -> async { secondary.replicate(id, message) } }
            .awaitAll()
    }

    private suspend fun ensureConnectedToSecondaries() = coroutineScope {
        if (hasDisconnectedSecondaries()) {
            getDisconnectedSecondaries()
                .map { secondary -> async { secondary.connect() } }
                .awaitAll()
        }
    }

    private suspend fun hasDisconnectedSecondaries(): Boolean {
        return secondaryServices.any { secondary -> !secondary.isConnected() }
    }

    private suspend fun getDisconnectedSecondaries(): List<SecondaryService> {
        return secondaryServices.filter { secondary -> !secondary.isConnected() }
    }
}
