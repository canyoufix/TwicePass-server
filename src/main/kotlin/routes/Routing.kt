package com.canyoufix.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        notesRouting()
        cardsRouting()
        passwordsRouting()

        get("/ping") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
