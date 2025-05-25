package com.canyoufix.dao

import com.canyoufix.entity.NoteEntity
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID

class NoteDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, NoteDao>(NoteEntity)

    var title by NoteEntity.title
    var content by NoteEntity.content
}