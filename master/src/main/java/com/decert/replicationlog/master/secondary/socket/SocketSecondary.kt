package com.decert.replicationlog.master.secondary.socket

import com.decert.replicationlog.master.extensions.getConnection
import com.decert.replicationlog.master.extensions.writeWithAck
import com.decert.replicationlog.master.models.Message
import com.decert.replicationlog.master.secondary.Secondary
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SocketSecondary(
    private val address: SocketAddress,
    private val selectorManager: SelectorManager
) : Secondary {

    private var connection: Connection? = null

    override suspend fun isConnected(): Boolean {
        return connection != null
    }

    fun isDisconnected(): Boolean {
        return connection == null
    }

    override suspend fun sendMessage(message: Message) {
        ensureConnected()

        connection?.writeWithAck(Json.encodeToString(message))
    }

    override suspend fun connect() {
        ensureDisconnected()

        connection = aSocket(selectorManager).tcp().connect(address.host, address.port).getConnection()
    }

    override suspend fun disconnect() {
        connection?.socket?.dispose()
        connection = null
    }

    private suspend fun ensureConnected() {
        if (!isConnected()) {
            throw IllegalStateException()
        }
    }

    private suspend fun ensureDisconnected() {
        if (isConnected()) {
            throw IllegalStateException()
        }
    }
}
