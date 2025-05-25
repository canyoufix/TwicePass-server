package com.canyoufix.dto

import kotlinx.serialization.Serializable

@Serializable
data class PasswordDto(
    val id: String,
    val title: String,
    val url: String,
    val username: String,
    val password: String
)