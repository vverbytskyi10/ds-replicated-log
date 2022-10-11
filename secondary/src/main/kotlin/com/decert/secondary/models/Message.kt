package com.decert.secondary.models

import kotlinx.serialization.Serializable

enum class Severity {
    VERBOSE, DEBUG, INFO, WARNING, ERROR
}

@Serializable
data class Message(val severity: Severity, val tag: String, val message: String)
