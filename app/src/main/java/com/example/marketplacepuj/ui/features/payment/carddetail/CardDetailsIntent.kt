package com.example.marketplacepuj.ui.features.payment.carddetail

sealed class CardDetailsIntent {
    data class EnterScreen(val cardId: String) : CardDetailsIntent()
    data class ToggleDeleteCardDialog(val isDialogShown: Boolean) : CardDetailsIntent()
    object ConfirmDeleteCard : CardDetailsIntent()
    data class SetCardAsPrimary(
        val cardId: String,
        val makePrimary: Boolean
    ) : CardDetailsIntent()
}
