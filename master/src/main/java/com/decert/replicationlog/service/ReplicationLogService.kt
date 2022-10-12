package com.decert.replicationlog.service

import com.decert.replicationlog.service.ReplicationService.MessageRequest
import com.decert.replicationlog.service.ReplicationService.MessageResponse

class ReplicationLogService : ReplicationGrpcKt.ReplicationCoroutineImplBase() {
    override suspend fun replicateMessage(request: MessageRequest): MessageResponse {
        return super.replicateMessage(request)
    }
}

class ReplicatedLogService : ReplicationGrpcKt.ReplicationCoroutineImplBase() {

    override suspend fun replicateMessage(request: MessageRequest): MessageResponse {
        return MessageResponse.newBuilder()
            .setId(0)
            .build()
    }
}