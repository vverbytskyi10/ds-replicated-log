package com.decert.replicationlog.secondary.plugins

import com.decert.replicationlog.secondary.SecondaryServer
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(server: SecondaryServer) {

    routing {
        get("/messages") {
            server.handleGetMessagesCall(call)
        }
    }
}
