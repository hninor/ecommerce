package com.example.marketplacepuj.ui.features.screen_add_card

import com.example.marketplacepuj.ui.features.screen_add_card.UiField

data class AddCardFormFields(
    val cardNumber: UiField = UiField(""),
    val cardHolder: UiField = UiField(""),
    val addressFirstLine: UiField = UiField(""),
    val addressSecondLine: UiField = UiField(""),
    val cvvCode: UiField = UiField(""),
    val expirationDate: UiField = UiField(""),
    val expirationDateTimestamp: Long? = null,
)

enum class AddCardFieldType {
    CARD_NUMBER,
    CARD_HOLDER,
    ADDRESS_LINE_1,
    ADDRESS_LINE_2,
    CVV_CODE,
    CARD_EXPIRATION_DATE
}
