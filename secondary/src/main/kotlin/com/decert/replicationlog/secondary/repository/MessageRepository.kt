package com.decert.replicationlog.secondary.repository

import com.decert.replicationlog.model.message.Message

interface MessageRepository {

    fun getMessages(): List<Message>

    fun addMessage(id: Int, message: Message)
}

class MessageRepositoryImpl : MessageRepository {

    private val messages = mutableMapOf<Int, Message>()

    override fun getMessages(): List<Message> {
        return messages.values.toList()
    }

    override fun addMessage(id: Int, message: Message) {
        messages[id] = message
    }
}
