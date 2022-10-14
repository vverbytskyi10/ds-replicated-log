package com.decert.replicationlog.master.models

import com.decert.replicationlog.service.ReplicationServiceGrpc.Message as GrpcMessage
import com.decert.replicationlog.service.ReplicationServiceGrpc.Severity as GrpcSeverity
import kotlinx.serialization.Serializable

enum class Severity {
    VERBOSE, DEBUG, INFO, WARNING, ERROR
}

@Serializable
data class Message(val severity: Severity, val tag: String, val message: String)

fun Message.toGrpcMessage(): GrpcMessage {
    return GrpcMessage.newBuilder()
        .setSeverity(severity.toGrpcSeverity())
        .setTag(tag)
        .setMessage(message)
        .build()
}

fun Severity.toGrpcSeverity(): GrpcSeverity {
    return when (this) {
        Severity.VERBOSE -> GrpcSeverity.VERBOSE
        Severity.DEBUG -> GrpcSeverity.DEBUG
        Severity.INFO -> GrpcSeverity.INFO
        Severity.WARNING -> GrpcSeverity.WARNING
        Severity.ERROR -> GrpcSeverity.ERROR
    }
}
