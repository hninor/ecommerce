package com.example.marketplacepuj.ui.features.messages.data.model

data class Chat(
    //var chatId: String,
    val fechaMensaje: Long,
    val idRemitente: String, // Puedes agregar más propiedades según tus necesidades
    var mensaje: String,
)

// Clase de datos para representar un chat
data class ChatSummary(val chatId: String?, val titleChat: String, val dateChat: Long)
