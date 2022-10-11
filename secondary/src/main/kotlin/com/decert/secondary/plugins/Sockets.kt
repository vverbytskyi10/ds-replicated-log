package com.decert.secondary.plugins

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.util.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlin.math.roundToInt

fun Application.configureSockets() {
    EchoApp.Server.main(emptyArray())
}

/**
 * Two mains are provided, you must first start EchoApp.Server, and then EchoApp.Client.
 * You can also start EchoApp.Server and then use a telnet client to connect to the echo server.
 */
object EchoApp {


    object Server {
        @JvmStatic
        fun main(args: Array<String>) {
            runBlocking {

            }
        }
    }
}

