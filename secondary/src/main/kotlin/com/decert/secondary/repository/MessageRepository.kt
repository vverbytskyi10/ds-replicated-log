package com.decert.secondary.repository

import com.decert.secondary.models.Message

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
