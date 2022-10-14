package com.decert.secondary

import com.decert.replicationlog.secondary.config.ServerConfig
import com.decert.secondary.plugins.configureMonitoring
import com.decert.secondary.plugins.configureRouting
import com.decert.secondary.plugins.configureSerialization
import com.decert.secondary.plugins.setupConfig
import com.decert.secondary.repository.MessageRepository
import com.decert.secondary.repository.MessageRepositoryImpl
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val config = ServerConfig()
    val repository: MessageRepository = MessageRepositoryImpl()
    val secondaryServer = SecondaryServer(config, repository).apply { startGrcpServer() }

    setupConfig(config)
    configureMonitoring()
    configureSerialization()
    configureRouting(secondaryServer)
}
