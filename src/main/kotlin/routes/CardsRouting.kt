package com.canyoufix.routes

import com.canyoufix.dao.CardDao
import com.canyoufix.dto.CardDto
import com.canyoufix.utils.EntityToDto.toCardDto
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.cardsRouting() {
    route("/cards") {
        get {
            val cards = transaction {
                CardDao.all().map { it.toCardDto() }
            }
            call.respond(cards)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)

            val card = transaction {
                CardDao.findById(id)?.toCardDto()
            }

            if (card == null) {
                call.respondText("Card not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(card)
            }
        }

        post {
            try {
                val cardDto = call.receive<CardDto>()

                transaction {
                    CardDao.new(cardDto.id) {
                        title = cardDto.title
                        number = cardDto.number
                        expiryDate = cardDto.expiryDate
                        cvc = cardDto.cvc
                        holderName = cardDto.holderName
                    }
                }

                call.respond(HttpStatusCode.Created, cardDto)
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

            val cardDto = call.receive<CardDto>()
            val updated = transaction {
                val card = CardDao.findById(idParam)
                if (card != null) {
                    card.title = cardDto.title
                    card.number = cardDto.number
                    card.expiryDate = cardDto.expiryDate
                    card.cvc = cardDto.cvc
                    card.holderName = cardDto.holderName
                    true
                } else {
                    false
                }
            }
            if (!updated) {
                call.respondText("Card not found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, cardDto)
            }
        }

        delete("/{id}") {
            val idParam = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id", status = HttpStatusCode.BadRequest
            )

            val deleted = transaction {
                CardDao.findById(idParam)?.let {
                    it.delete()
                    true
                } ?: false
            }

            if (!deleted) {
                call.respondText("Card not found", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Card deleted", status = HttpStatusCode.OK)
            }
        }
    }
}
