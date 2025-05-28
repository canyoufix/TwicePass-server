package com.canyoufix.routes

import com.canyoufix.dao.NoteDao
import com.canyoufix.dto.NoteDto
import com.canyoufix.utils.EntityToDto.toNoteDto
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.notesRouting() {
    route("/notes") {
        get {
            val notes = transaction {
                NoteDao.all().map { it.toNoteDto() }
            }
            call.respond(notes)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)

            val note = transaction {
                NoteDao.findById(id)?.toNoteDto()
            }

            if (note == null) {
                call.respondText("Note not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(note)
            }
        }

        post {
            try {
                val noteDto = call.receive<NoteDto>()

                transaction {
                    NoteDao.new(noteDto.id) {
                        title = noteDto.title
                        content = noteDto.content
                        lastModified = noteDto.lastModified
                        isDeleted = noteDto.isDeleted
                    }
                }

                call.respond(HttpStatusCode.Created, noteDto)
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

            val noteDto = call.receive<NoteDto>()
            val updated = transaction {
                val note = NoteDao.findById(idParam)
                if (note != null) {
                    note.title = noteDto.title
                    note.content = noteDto.content
                    note.lastModified = noteDto.lastModified
                    note.isDeleted = noteDto.isDeleted
                    true
                } else {
                    false
                }
            }
            if (!updated) {
                call.respondText("Note not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, noteDto)
            }
        }

        delete("/{id}") {
            val idParam = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id", status = HttpStatusCode.BadRequest
            )

            val noteDto = call.receive<NoteDto>()
            val deleted = transaction {
                val note = NoteDao.findById(idParam)
                if (note != null) {
                    note.isDeleted = noteDto.isDeleted
                    note.lastModified = noteDto.lastModified
                    true
                } else {
                    false
                }
            }

            if (!deleted) {
                call.respondText("Note not found", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Note deleted", status = HttpStatusCode.OK)
            }
        }
    }
}