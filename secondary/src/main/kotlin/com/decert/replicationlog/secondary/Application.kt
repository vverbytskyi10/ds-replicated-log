package com.decert.replicationlog.secondary

import com.decert.replicationlog.secondary.config.ServerConfig
import com.decert.replicationlog.secondary.plugins.configureMonitoring
import com.decert.replicationlog.secondary.plugins.configureRouting
import com.decert.replicationlog.secondary.plugins.configureSerialization
import com.decert.replicationlog.secondary.plugins.setupConfig
import com.decert.replicationlog.secondary.repository.MessageRepository
import com.decert.replicationlog.secondary.repository.MessageRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val config = ServerConfig()
    setupConfig(config)

    val repository: MessageRepository = MessageRepositoryImpl()
    val secondaryServer = SecondaryServer(config, repository).apply { startGrcpServer() }

    configureMonitoring()
    configureSerialization()
    configureRouting(secondaryServer)
}
