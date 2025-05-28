package com.canyoufix.entity

import org.jetbrains.exposed.dao.id.IdTable

object CardEntity : IdTable<String>("cards") {
    override val id = varchar("id", 36).entityId()

    val title = varchar("title", 255)
    val number = varchar("number", 255)
    val expiryDate = varchar("expiry_date", 255)
    val cvc = varchar("cvc", 255)
    val holderName = varchar("holder_name", 255)

    val lastModified = long("last_modified")
    val isDeleted = bool("is_deleted")

    override val primaryKey = PrimaryKey(id)
}
