package com.decert.replicationlog.master.repository

import com.decert.replicationlog.model.message.Message

interface MessageRepository {

    fun getMessages(): List<Message>

    fun addMessage(id: Int, message: Message)
}

class MessageRepositoryImpl : MessageRepository {

    private val messages = mutableListOf<Message>()

    override fun getMessages(): List<Message> {
        return messages
    }

    override fun addMessage(id: Int, message: Message) {
        messages.add(message)
    }
}
