package com.canyoufix.entity

import org.jetbrains.exposed.dao.id.IdTable

object CardEntity : IdTable<String>("cards") {
    override val id = varchar("id", 36).entityId()
    val title = varchar("title", 255)
    val cardNumber = varchar("card_number", 20)
    val expiryDate = varchar("expiry_date", 10)
    val cvc = varchar("cvc", 5)
    val cardHolder = varchar("card_holder", 255)

    override val primaryKey = PrimaryKey(id)
}