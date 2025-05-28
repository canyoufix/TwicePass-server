package com.canyoufix.dto

import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: String,

    val title: String,
    val number: String,
    val expiryDate: String,
    val cvc: String,
    val holderName: String,
    
    val lastModified: Long,
    val isDeleted: Boolean
)
