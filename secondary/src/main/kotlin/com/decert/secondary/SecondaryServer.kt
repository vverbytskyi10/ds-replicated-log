package com.decert.secondary

import com.decert.secondary.models.Message
import com.decert.secondary.storage.MessageStorage
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val selectorManager = ActorSelectorManager(Dispatchers.IO)
val DefaultPort = 9008

private const val MSG_START = 2
private const val MSG_END = 4
private const val MSG_SHUT_DOWN = 16

private const val MSG_ACK = 8

class SecondaryServer(private val storage: MessageStorage) {

    private val listeningScope = CoroutineScope(Dispatchers.IO)

    fun startListening() {
        listeningScope.launch {
            val serverSocket = aSocket(selectorManager).tcp().bind(port = DefaultPort)
            println("Echo Server listening at ${serverSocket.localAddress}")
            while (true) {
                val socket = serverSocket.accept()
                println("Accepted $socket")
                launch {
                    val input = socket.openReadChannel()
                    val output = socket.openWriteChannel(autoFlush = true)
                    try {
                        var messageString = ""

                        while (true) {
                            println("Awaiting a command from Master")
                            when (val command = input.readInt()) {
                                MSG_START -> {
                                    println("Start Message command is received")
                                    //while (command != MSG_END) {
                                    messageString = messageString.plus(input.readUTF8Line().orEmpty())
                                    //}
                                }

                                MSG_END -> {
                                    println("End Message command is received")
                                    println("Final Message is: $messageString")
                                    messageString = ""
                                    output.writeInt(MSG_ACK)
                                    println("Acknowledgement is sent back")
                                }

                                MSG_SHUT_DOWN -> break
                            }
                        }
                    } catch (e: Throwable) {
                        socket.close()
                    }
                }
            }
        }
    }

    fun getMessages(): List<Message> {
        return storage.getMessages()
    }
}