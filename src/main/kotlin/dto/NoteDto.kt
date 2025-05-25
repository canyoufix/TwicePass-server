package com.canyoufix.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String,
    val title: String,
    val content: String
)

