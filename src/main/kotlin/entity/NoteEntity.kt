package com.canyoufix.entity

import org.jetbrains.exposed.dao.id.IdTable

object NoteEntity : IdTable<String>("notes") {
    override val id = varchar("id", 36).entityId()

    val title = varchar("title", 255)
    val content = text("content")

    val lastModified = long("last_modified")
    val isDeleted = bool("is_deleted")

    override val primaryKey = PrimaryKey(id)
}
