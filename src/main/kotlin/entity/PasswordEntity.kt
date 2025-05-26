package com.canyoufix.entity

import org.jetbrains.exposed.dao.id.IdTable

object PasswordEntity : IdTable<String>("passwords") {
    override val id = varchar("id", 36).entityId()

    val title = varchar("title", 255)
    val url = varchar("url", 255)
    val username = varchar("username", 255)
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}