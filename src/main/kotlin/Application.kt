package com.canyoufix

import com.canyoufix.database.initDatabase
import com.canyoufix.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.0.11", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initDatabase()
    configureSerialization()
    configureRouting()
}
