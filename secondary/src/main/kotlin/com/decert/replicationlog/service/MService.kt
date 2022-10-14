package com.decert.replicationlog.service

import com.decert.replicationlog.service.ReplicationServiceGrpc.MessageRequest
import com.decert.replicationlog.service.ReplicationServiceGrpc.MessageResponse
import com.decert.secondary.models.toMessage
import com.decert.secondary.repository.MessageRepository
import kotlinx.coroutines.withTimeout

class MService(
    private val mServiceParams: MServiceParams,
    private val messageRepository: MessageRepository
) : ReplicationGrpcKt.ReplicationCoroutineImplBase() {

    override suspend fun replicateMessage(request: MessageRequest): MessageResponse {
        return withTimeout(mServiceParams.delay) {
            val id = request.id
            val message = request.message.toMessage()

            messageRepository.addMessage(id, message)

            MessageResponse.newBuilder().setId(id).build()
        }
    }
}
