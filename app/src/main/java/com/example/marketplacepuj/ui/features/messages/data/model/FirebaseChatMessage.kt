package com.example.marketplacepuj.ui.features.messages.data.model

data class FirebaseChatMessage(
    val fechaMensaje: Long? = null, // Haz que fechaMensaje sea nullable
    val idRemitente: String? = null,
    val mensaje: String? = null
)
