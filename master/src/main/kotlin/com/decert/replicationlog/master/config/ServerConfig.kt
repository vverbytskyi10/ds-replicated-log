package com.decert.replicationlog.master.config

class ServerConfig {

    lateinit var secondaryConfig: SecondaryConfig

    companion object {

        val WRITE_TIMEOUT = 2000L
        val ACKNOWLEDGEMENT_TIMEOUT = 2000L
    }
}
