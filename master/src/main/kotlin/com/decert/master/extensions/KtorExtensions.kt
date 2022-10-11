package com.decert.master.extensions

import com.decert.master.config.ServerConfig
import com.decert.master.socket.SocketConstants
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.withTimeout

fun Socket.getConnection(): Connection {
    return Connection(this, openReadChannel(), openWriteChannel(true))
}

suspend fun Connection.write(string: String, withTimeout: Long = ServerConfig.WRITE_TIMEOUT) {
    withTimeout(withTimeout) {
        output.writeInt(SocketConstants.MSG_START)
        output.writeStringUtf8(string)
        output.writeInt(SocketConstants.MSG_END)
    }
}

suspend fun Connection.writeWithAck(
    string: String,
    writeTimeout: Long = ServerConfig.WRITE_TIMEOUT,
    acknowledgementTimeout: Long = ServerConfig.ACKNOWLEDGEMENT_TIMEOUT
) {
    write(string.plus("\n"), writeTimeout)

    val ackStatus = withTimeout(acknowledgementTimeout) { input.readInt() }

    if (ackStatus != SocketConstants.MSG_ACK) {
        throw IllegalStateException()
    }
}
