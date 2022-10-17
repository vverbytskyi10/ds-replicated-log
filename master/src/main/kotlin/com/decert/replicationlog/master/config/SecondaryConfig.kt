package com.decert.replicationlog.master.config

data class Address(val host: String, val port: Int)

data class SecondaryConfig(val addresses: List<Address>)
