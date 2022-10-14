package com.decert.replicationlog.model.message

import kotlinx.serialization.Serializable

@Serializable
data class Message(val severity: Severity, val tag: String, val message: String)

enum class Severity { VERBOSE, DEBUG, INFO, WARNING, ERROR }
