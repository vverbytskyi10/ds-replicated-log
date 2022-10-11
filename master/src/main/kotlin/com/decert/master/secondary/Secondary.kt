package com.decert.master.secondary

import com.decert.master.models.Message

interface Secondary {

    suspend fun connect()

    suspend fun disconnect()

    suspend fun isConnected(): Boolean

    suspend fun sendMessage(message: Message)
}
