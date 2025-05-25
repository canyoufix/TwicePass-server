package com.canyoufix.database

import com.canyoufix.entity.CardEntity
import com.canyoufix.entity.NoteEntity
import com.canyoufix.entity.PasswordEntity
import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDatabase() {
    Database.connect("jdbc:sqlite:TwicePass-server.db", driver = "org.sqlite.JDBC")

    transaction {
        SchemaUtils.create(PasswordEntity, CardEntity, NoteEntity)
    }
}