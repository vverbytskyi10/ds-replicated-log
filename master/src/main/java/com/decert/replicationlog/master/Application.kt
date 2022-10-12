package com.decert.replicationlog.master

import com.decert.replicationlog.master.config.ServerConfig
import com.decert.replicationlog.master.plugins.configureRouting
import com.decert.replicationlog.master.plugins.configureSerialization
import com.decert.replicationlog.master.plugins.setupConfig
import com.decert.replicationlog.master.storage.InMemoryMessageStorage
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
