package com.decert.replicationlog.master.config

class ServerConfig {

    lateinit var secondaryConfig: SecondaryConfig

    companion object {

        const val WRITE_TIMEOUT = 2000L
    }
}
