package com.canyoufix.routes

import com.canyoufix.dao.CardDao
import com.canyoufix.dao.NoteDao
import com.canyoufix.dao.PasswordDao
import com.canyoufix.dto.NoteDto
import com.canyoufix.dto.PasswordDto
import com.canyoufix.utils.EntityToDTO.toNoteDto
import com.canyoufix.utils.EntityToDTO.toPasswordDto
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.passwordsRouting() {
    route("/passwords") {
        get {
            val passwords = transaction {
                PasswordDao.all().map { it.toPasswordDto() }
            }
            call.respond(passwords)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)

            val password = transaction {
                PasswordDao.findById(id)?.toPasswordDto()
            }

            if (password == null) {
                call.respondText("Password not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(password)
            }
        }

        post {
            try {
                val passwordDto = call.receive<PasswordDto>()

                transaction {
                    PasswordDao.new(passwordDto.id) {
                        title = passwordDto.title
                        url = passwordDto.url
                        username = passwordDto.username
                        password = passwordDto.password
                    }
                }

                call.respond(HttpStatusCode.Created, passwordDto)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Ошибка при создании: ${e.localizedMessage}")
                )
            }
        }

        put("/{id}") {
            val idParam = call.parameters["id"] ?: return@put call.respondText(
                "Missing id", status = HttpStatusCode.BadRequest
            )

            val passwordDto = call.receive<PasswordDto>()
            val updated = transaction {
                val password = PasswordDao.findById(idParam)
                if (password != null) {
                    password.title = passwordDto.title
                    password.url = passwordDto.url
                    password.username = passwordDto.username
                    password.password = passwordDto.password
                    true
                } else {
                    false
                }
            }
            if (!updated) {
                call.respondText("Password not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, passwordDto)
            }
        }

        delete("/{id}") {
            val idParam = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id", status = HttpStatusCode.BadRequest
            )

            val deleted = transaction {
                PasswordDao.findById(idParam)?.let {
                    it.delete()
                    true
                } ?: false
            }

            if (!deleted) {
                call.respondText("Password not found", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Password deleted", status = HttpStatusCode.OK)
            }
        }
    }
}
