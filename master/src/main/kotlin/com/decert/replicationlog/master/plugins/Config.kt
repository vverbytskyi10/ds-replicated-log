package com.decert.replicationlog.master.plugins

import com.decert.replicationlog.master.config.Address
import com.decert.replicationlog.master.config.SecondaryConfig
import com.decert.replicationlog.master.config.ServerConfig
import io.ktor.server.application.*

private const val OBJECT_SECONDARY = "ktor.secondary"

private const val PATH_ADDRESSES = "addresses"

fun Application.setupConfig(config: ServerConfig) {
    val secondaryObject = environment.config.config(OBJECT_SECONDARY)
    val addresses = secondaryObject.property(PATH_ADDRESSES)
        .getString()
        .split(';')
        .map { string -> string.split(':').let { strings -> Address(strings[0], strings[1].toInt()) } }

    config.secondaryConfig = SecondaryConfig(addresses)
}
