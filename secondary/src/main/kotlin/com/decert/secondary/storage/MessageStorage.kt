package com.decert.secondary.storage

import com.decert.secondary.models.Message

interface MessageStorage {

    fun getMessages(): List<Message>

    fun addMessage(message: Message)
}

class InMemoryMessageStorage : MessageStorage {

    private val messages = mutableListOf<Message>()

    override fun getMessages(): List<Message> {
        return messages
    }

    override fun addMessage(message: Message) {
        messages.add(message)
    }
}
