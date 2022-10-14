package com.decert.replicationlog.master

import com.decert.replicationlog.master.config.ServerConfig
import com.decert.replicationlog.master.models.Message
import com.decert.replicationlog.master.secondary.ServiceAddress
import com.decert.replicationlog.master.secondary.SecondaryService
import com.decert.replicationlog.master.secondary.impl.SecondaryServiceImpl
import com.decert.replicationlog.master.repository.MessageRepository
import com.decert.replicationlog.master.secondary.ServiceParams
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger

class MasterServer(
    private val serverConfig: ServerConfig,
    private val messageRepository: MessageRepository
) {

    private val messageId = AtomicInteger()
    private val secondaryServices: List<SecondaryService> = serverConfig.secondaryConfig.ports
        .map { port ->
            SecondaryServiceImpl(
                ServiceParams(
                    ServiceAddress(serverConfig.secondaryConfig.host, port),
                    ServerConfig.WRITE_TIMEOUT
                ),
            )
        }

    fun getMessages(): List<Message> {
        return messageRepository.getMessages()
    }

    suspend fun saveMessage(message: Message): Int {
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
