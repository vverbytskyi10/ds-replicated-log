package com.decert.replicationlog.service

import com.decert.replicationlog.secondary.mappers.toMessage
import com.decert.replicationlog.service.MessageServiceGrpc.ReplicateMessageRequest
import com.decert.replicationlog.service.MessageServiceGrpc.ReplicateMessageResponse
import com.decert.replicationlog.secondary.repository.MessageRepository
import kotlinx.coroutines.withTimeout

class MessageService(
    private val params: Params,
    private val messageRepository: MessageRepository
) : MessageGrpcKt.MessageCoroutineImplBase() {

    override suspend fun replicate(request: ReplicateMessageRequest): ReplicateMessageResponse {
        return withTimeout(params.delay) {
            val id = request.id
            val message = request.message.toMessage()

            messageRepository.addMessage(id, message)

            ReplicateMessageResponse.newBuilder().setId(id).build()
        }
    }

    data class Params(val delay: Long)
}
