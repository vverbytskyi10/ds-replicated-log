package com.decert.master.config

data class SecondaryConfig(
    val host: String,
    val ports: Set<Int>
)
