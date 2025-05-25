package com.canyoufix.dto

import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: String,
    val title: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvc: String,
    val cardHolder: String
)