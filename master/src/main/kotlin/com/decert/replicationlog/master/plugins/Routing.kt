package com.decert.replicationlog.master.plugins

import com.decert.replicationlog.master.MasterServer
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(masterServer: MasterServer) {

    routing {
        route("/message") {
            post {
                masterServer.handlePostMessageCall(call)
            }
        }
        route("/messages") {
            get {
                masterServer.handleGetMessagesCall(call)
            }
        }
    }
}
