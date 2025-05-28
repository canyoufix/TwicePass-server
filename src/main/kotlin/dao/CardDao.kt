package com.canyoufix.dao

import com.canyoufix.entity.CardEntity
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID

class CardDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, CardDao>(CardEntity)

    var title by CardEntity.title
    var number by CardEntity.number
    var expiryDate by CardEntity.expiryDate
    var cvc by CardEntity.cvc
    var holderName by CardEntity.holderName

    var lastModified by CardEntity.lastModified
    var isDeleted by CardEntity.isDeleted
}