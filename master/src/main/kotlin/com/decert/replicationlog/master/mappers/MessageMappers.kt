package com.decert.replicationlog.master.mappers

import com.decert.replicationlog.model.message.Message
import com.decert.replicationlog.model.message.Severity
import com.decert.replicationlog.service.MessageServiceGrpc.GrpcMessage
import com.decert.replicationlog.service.MessageServiceGrpc.GrpcSeverity

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
