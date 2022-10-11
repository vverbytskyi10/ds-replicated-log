package com.decert.master

import com.decert.master.config.ServerConfig
import com.decert.master.plugins.configureRouting
import com.decert.master.plugins.configureSerialization
import com.decert.master.plugins.setupConfig
import com.decert.master.storage.InMemoryMessageStorage
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val config = ServerConfig()
    val storage = InMemoryMessageStorage()

    setupConfig(config)

    log.debug("Secondary Host: ${config.secondaryConfig.host}")
    log.debug("Secondary Ports: ${config.secondaryConfig.ports}")

    val masterServer = MasterServer(config, storage)

    configureSerialization()
    configureRouting(masterServer)
}
