package com.decert.master.plugins

import com.decert.master.config.SecondaryConfig
import com.decert.master.config.ServerConfig
import io.ktor.server.application.*

private const val OBJECT_SECONDARY = "ktor.secondary"

private const val PATH_HOST = "host"
private const val PATH_PORTS = "ports"

fun Application.setupConfig(config: ServerConfig) {
    val secondaryObject = environment.config.config(OBJECT_SECONDARY)
    val host = secondaryObject.property(PATH_HOST).getString()
    val ports = secondaryObject.property(PATH_PORTS).getList().map { string -> string.toInt() }.toSet()

    config.secondaryConfig = SecondaryConfig(host, ports)
}
