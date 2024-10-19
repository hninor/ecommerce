package com.example.marketplacepuj.ui.features.cards.model

data class AddCardPayload(
    val cardNumber: String,
    val cardHolder: String,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val cvvCode: String,
    val expirationDate: Long,
)
