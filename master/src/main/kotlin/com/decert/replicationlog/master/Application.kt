package com.decert.replicationlog.master

import com.decert.replicationlog.master.config.ServerConfig
import com.decert.replicationlog.master.plugins.configureRouting
import com.decert.replicationlog.master.plugins.configureSerialization
import com.decert.replicationlog.master.plugins.setupConfig
import com.decert.replicationlog.master.repository.MessageRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val config = ServerConfig()
    val storage = MessageRepositoryImpl()

    setupConfig(config)

    val masterServer = MasterServer(config, log, storage)

    configureSerialization()
    configureRouting(masterServer)
}
