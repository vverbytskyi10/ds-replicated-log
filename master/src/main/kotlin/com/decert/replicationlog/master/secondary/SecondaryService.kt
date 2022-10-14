package com.decert.replicationlog.master.secondary

import com.decert.replicationlog.master.models.Message

interface SecondaryService {

    suspend fun connect()

    suspend fun disconnect()

    suspend fun isConnected(): Boolean

    suspend fun replicate(id: Int, message: Message)
}
