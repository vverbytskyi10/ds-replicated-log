package com.decert.replicationlog.secondary.mappers

import com.decert.replicationlog.model.message.Message
import com.decert.replicationlog.model.message.Severity
import com.decert.replicationlog.service.MessageServiceGrpc
import com.decert.replicationlog.service.MessageServiceGrpc.GrpcSeverity

fun MessageServiceGrpc.GrpcMessage.toMessage(): Message {
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
