package com.decert.master

import com.decert.master.config.ServerConfig
import com.decert.master.models.Message
import com.decert.master.secondary.Secondary
import com.decert.master.secondary.socket.SocketAddress
import com.decert.master.secondary.socket.SocketSecondary
import com.decert.master.storage.MessageStorage
import io.ktor.network.selector.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MasterServer(
    private val serverConfig: ServerConfig,
    private val messageStorage: MessageStorage
) {

    private val selectorManager = ActorSelectorManager(Dispatchers.IO)

    private val secondaries: List<Secondary> = serverConfig.secondaryConfig.ports.map { port ->
        SocketSecondary(
            SocketAddress(serverConfig.secondaryConfig.host, port),
            selectorManager
        )
    }

    fun getMessages(): List<Message> {
        return messageStorage.getMessages()
    }

    suspend fun saveMessage(message: Message) {
        ensureConnectedToSecondaries()
        sendMessageToSecondaries(message)
        messageStorage.addMessage(message)
    }

    private suspend fun sendMessageToSecondaries(message: Message) = coroutineScope {
        secondaries
            .map { secondary -> async { secondary.sendMessage(message) } }
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
        return secondaries.any { secondary -> !secondary.isConnected() }
    }

    private suspend fun getDisconnectedSecondaries(): List<Secondary> {
        return secondaries.filter { secondary -> !secondary.isConnected() }
    }
}
