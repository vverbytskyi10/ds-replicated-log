package com.decert.replicationlog.service

import com.decert.replicationlog.master.models.Message
import com.decert.replicationlog.master.models.toGrpcMessage
import com.decert.replicationlog.service.ReplicationGrpcKt.ReplicationCoroutineStub
import io.grpc.ManagedChannel

class MessageService(channel: ManagedChannel) {

    private val stub = ReplicationCoroutineStub(channel)

    suspend fun replicate(id: Int, message: Message) {
        val rpcMessage = message.toGrpcMessage()

        val replicateRequest = ReplicationServiceGrpc.MessageRequest.newBuilder()
            .setId(id)
            .setMessage(rpcMessage)
            .build()

        stub.replicateMessage(replicateRequest)
    }
}
