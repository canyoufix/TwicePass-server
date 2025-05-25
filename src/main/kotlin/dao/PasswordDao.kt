package com.canyoufix.dao

import com.canyoufix.entity.PasswordEntity
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID

class PasswordDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, PasswordDao>(PasswordEntity)

    var title by PasswordEntity.title
    var url by PasswordEntity.url
    var username by PasswordEntity.username
    var password by PasswordEntity.password
}