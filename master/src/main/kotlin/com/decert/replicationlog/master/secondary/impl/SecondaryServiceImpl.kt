package com.decert.replicationlog.master.secondary.impl

import com.decert.replicationlog.master.secondary.SecondaryService
import com.decert.replicationlog.master.secondary.ServiceParams
import com.decert.replicationlog.model.message.Message
import com.decert.replicationlog.service.MessageService
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

class SecondaryServiceImpl(private val serviceParams: ServiceParams) : SecondaryService {

    private var channel: ManagedChannel? = null
    private var messageService: MessageService? = null

    private var isConnected: Boolean = false

    override suspend fun connect() {
        assertDisconnected()

        channel = ManagedChannelBuilder
            .forAddress(serviceParams.serviceAddress.host, serviceParams.serviceAddress.port)
            .usePlaintext()
            .build()
        messageService = MessageService(channel!!)

        isConnected = true
    }

    override suspend fun disconnect() {
        assertConnected()

        channel?.awaitTermination(1, TimeUnit.SECONDS)

        channel = null
        messageService = null
        isConnected = false
    }

    override suspend fun isConnected(): Boolean {
        return try {
            assertDisconnected()
            false
        } catch (e: IllegalStateException) {
            true
        }
    }

    override suspend fun replicate(id: Int, message: Message) {
        assertConnected()

        withTimeout(serviceParams.timeout) { messageService?.replicate(id, message) }
    }

    private fun assertConnected() {
        assert(isConnected && channel != null && messageService != null)
    }

    private fun assertDisconnected() {
        assert(!isConnected && channel == null && messageService == null)
    }
}
