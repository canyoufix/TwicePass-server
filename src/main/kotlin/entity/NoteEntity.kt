package com.canyoufix.entity

import org.jetbrains.exposed.dao.id.IdTable

object NoteEntity : IdTable<String>("notes") {
    override val id = varchar("id", 36).entityId()

    val title = varchar("title", 255)
    val content = text("content")

    override val primaryKey = PrimaryKey(id)
}