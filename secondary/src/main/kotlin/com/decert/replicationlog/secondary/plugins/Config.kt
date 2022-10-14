package com.decert.replicationlog.secondary.plugins

import com.decert.replicationlog.secondary.config.GrpcConfig
import com.decert.replicationlog.secondary.config.ServerConfig
import io.ktor.server.application.*

private const val OBJECT_GRPC_CONFIG = "ktor.grpc_config"

private const val PATH_PORT = "grpc_port"
private const val PATH_CALL_DELAY = "grpc_call_delay"

fun Application.setupConfig(config: ServerConfig) {
    val grpcObject = environment.config.config(OBJECT_GRPC_CONFIG)

    val port = grpcObject.property(PATH_PORT).getString().toInt()
    val delay = grpcObject.property(PATH_CALL_DELAY).getString().toLong()

    config.grpcConfig = GrpcConfig(port, delay)
}