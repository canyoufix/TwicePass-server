package com.canyoufix.utils

import com.canyoufix.dao.CardDao
import com.canyoufix.dao.NoteDao
import com.canyoufix.dao.PasswordDao
import com.canyoufix.dto.CardDto
import com.canyoufix.dto.NoteDto
import com.canyoufix.dto.PasswordDto

object EntityToDTO {

    fun NoteDao.toNoteDto(): NoteDto {
        return NoteDto(
            id = this.id.value,
            title = this.title,
            content = this.content
        )
    }

    fun PasswordDao.toPasswordDto(): PasswordDto {
        return PasswordDto(
            id = this.id.value,
            title = this.title,
            url = this.url,
            username = this.username,
            password = this.password
        )
    }

    fun CardDao.toCardDto(): CardDto {
        return CardDto(
            id = this.id.value,
            title = this.title,
            cardNumber = this.cardNumber,
            expiryDate = this.expiryDate,
            cvc = this.cvc,
            cardHolder = this.cardHolder
        )
    }
}
