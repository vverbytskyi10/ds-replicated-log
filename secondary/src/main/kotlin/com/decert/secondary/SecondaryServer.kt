package com.decert.secondary

import com.decert.replicationlog.secondary.config.ServerConfig
import com.decert.replicationlog.service.MService
import com.decert.replicationlog.service.MServiceParams
import com.decert.secondary.repository.MessageRepository
import io.grpc.ServerBuilder
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondaryServer(config: ServerConfig, private val repository: MessageRepository) {

    private val listeningScope = CoroutineScope(Dispatchers.IO)

    private val server = ServerBuilder
        .forPort(config.grpcConfig.port)
        .addService(MService(MServiceParams(config.grpcConfig.delay), repository))
        .build()

    fun startGrcpServer() {
        listeningScope.launch {
            server.start()
            server.awaitTermination()
        }
    }

    fun stopGrcpServer() {
        listeningScope.launch {
            server.shutdown()
            server.awaitTermination()
        }
    }

    suspend fun handleGetMessagesCall(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, mapOf("messages" to repository.getMessages()))
    }
}