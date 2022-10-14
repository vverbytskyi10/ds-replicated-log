package com.decert.secondary.models

import kotlinx.serialization.Serializable
import com.decert.replicationlog.service.ReplicationServiceGrpc.Message as GrpcMessage
import com.decert.replicationlog.service.ReplicationServiceGrpc.Severity as GrpcSeverity

enum class Severity {
    VERBOSE, DEBUG, INFO, WARNING, ERROR
}

@Serializable
data class Message(val severity: Severity, val tag: String, val message: String)

fun GrpcMessage.toMessage(): Message {
    return Message(severity.toSeverity(), tag, message)
}

fun GrpcSeverity.toSeverity(): Severity {
    return when (this) {
        GrpcSeverity.VERBOSE -> Severity.VERBOSE
        GrpcSeverity.INFO -> Severity.INFO
        GrpcSeverity.DEBUG -> Severity.DEBUG
        GrpcSeverity.WARNING -> Severity.WARNING
        GrpcSeverity.ERROR -> Severity.ERROR
        else -> throw IllegalArgumentException()
    }
}
