package com.decert.replicationlog.service

import com.decert.replicationlog.master.mappers.toGrpcMessage
import com.decert.replicationlog.model.message.Message
import io.grpc.ManagedChannel

class MessageService(channel: ManagedChannel) {

    private val stub = MessageGrpcKt.MessageCoroutineStub(channel)

    suspend fun replicate(id: Int, message: Message) {
        val rpcMessage = message.toGrpcMessage()

        val replicateRequest = MessageServiceGrpc.ReplicateMessageRequest.newBuilder()
            .setId(id)
            .setMessage(rpcMessage)
            .build()

        stub.replicate(replicateRequest)
    }
}
