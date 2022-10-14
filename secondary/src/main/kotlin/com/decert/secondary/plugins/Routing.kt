package com.decert.secondary.plugins

import com.decert.secondary.SecondaryServer
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(server: SecondaryServer) {

    routing {
        get("/messages") {
            server.handleGetMessagesCall(call)
        }
    }
}
