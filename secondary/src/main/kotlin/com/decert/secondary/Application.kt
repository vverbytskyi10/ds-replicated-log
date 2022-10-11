package com.decert.secondary

import com.decert.secondary.plugins.configureMonitoring
import com.decert.secondary.plugins.configureRouting
import com.decert.secondary.plugins.configureSerialization
import com.decert.secondary.plugins.configureSockets
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val server = SecondaryServer().apply { startListening() }
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}
