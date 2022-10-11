package com.decert.master.plugins

import com.decert.master.MasterServer
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting(masterServer: MasterServer) {

    routing {
        route("/message") {
            post {
                try {
                    masterServer.saveMessage(call.receive())
                    call.respond(HttpStatusCode.OK, "Success")
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.NotAcceptable, "Failure: Invalid message format")
                }
            }
        }
        route("/messages") {
            get {
                call.respond(HttpStatusCode.OK, mapOf("messages" to masterServer.getMessages()))
            }
        }
    }
}
