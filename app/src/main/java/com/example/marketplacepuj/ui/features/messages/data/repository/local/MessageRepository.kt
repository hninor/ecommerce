package com.example.marketplacepuj.ui.features.messages.data.repository.local

import android.util.Log
import com.example.marketplacepuj.ui.features.messages.data.model.Chat
import com.example.marketplacepuj.ui.features.messages.data.model.ChatSummary
import com.example.marketplacepuj.ui.features.messages.data.model.FirebaseChatMessage
import com.example.marketplacepuj.ui.features.messages.data.model.Message
import com.example.marketplacepuj.ui.features.messages.data.repository.remote.sendNotification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class MessageRepository {

    private val database = FirebaseDatabase.getInstance()


    suspend fun getChat(chatId: String): Chat? {
        return try {
            val chatSnapshot =
                database.getReference("$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION").get()
                    .await()

            if (chatSnapshot.exists()) {
                // Obtener el último mensaje del chat (sin orderByChild)
                val mensajesSnapshot =
                    database.getReference("$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION")
                        .limitToLast(1) // Obtener solo el último mensaje
                        .get()
                        .await()

                if (mensajesSnapshot.exists()) {
                    println("MensajesSnapshot: $mensajesSnapshot")
                    val ultimoMensajeSnapshot =
                        mensajesSnapshot.children.first() // Obtener el primer (y último) resultado
                    val fecha = ultimoMensajeSnapshot.child("fecha").getValue(Long::class.java)
                    val ultimoMensaje =
                        ultimoMensajeSnapshot.child("mensaje").getValue(String::class.java)
                    val idRemitente =
                        ultimoMensajeSnapshot.child("idRemitente").getValue(String::class.java)
                    println("UltimoMensaje: $ultimoMensaje y remitente $idRemitente")
                    return Chat(fecha!!, idRemitente!!, ultimoMensaje!!)
                }
            }

            null // Chat no encontrado o sin mensajes
        } catch (e: Exception) {
            // Manejo de errores
            println("Error al obtener el chat: ${e.message}")
            null
        }

    }

    fun sendMessage(messageText: String, chatId: String, userId: String): Chat? {
        return try {
            val chatRef = database.getReference("$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION")
            // Obtener la fecha y hora actual en milisegundos
            val timestamp = System.currentTimeMillis()
            val message = Chat(timestamp, userId, messageText)
            chatRef.push().setValue(message)
            val chatMembersRef =
                database.getReference("$CHATS_COLLECTION/$chatId/$MEMBERS_COLLECTION")
            chatMembersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val participants =
                        snapshot.getValue<List<String>>() // Obtener la lista de participantes
                    if (participants != null && participants.size == 2) { // Asegurarse de que hay dos participantes
                        val adminId =
                            participants.firstOrNull { it != userId } // El administrador es el que NO es el userId actual
                        if (adminId != null) {
                            // Enviar la notificación al administrador
                            sendNotification(adminId, messageText)
                            Log.e(TAG, "Notificación enviada")
                        } else {
                            Log.e(TAG, "No se pudo determinar el ID del administrador")
                        }
                    } else {
                        Log.e(
                            TAG,
                            "Error al obtener los participantes del chat o número de participantes incorrecto"
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error al obtener los miembros del chat", error.toException())
                }
            })
            message
        } catch (e: Exception) {
            println("Error al insertar el chat: ${e.message}")
            null
        }
    }

    suspend fun getMessagesForChat(chatId: String, userId: String): List<Chat> {
        val messages = mutableListOf<Chat>()
        return try {
            val snapshot =
                database.getReference("$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION").get()
                    .await()
            for (messageSnapshot in snapshot.children) {
                val firebaseChat = messageSnapshot.getValue(FirebaseChatMessage::class.java)
                val userChat = if (userId == firebaseChat?.idRemitente) "Usuario" else "Vendedor"
                firebaseChat?.let {
                    messages.add(Chat(it.fechaMensaje!!, userChat, it.mensaje!!)) // Mapear a Chat
                }
            }
            messages
        } catch (e: Exception) {
            // Manejo de errores
            println("Error al obtener mensajes: ${e.message}")
            emptyList()
        }
    }

    suspend fun getChatsForUser(userId: String): List<ChatSummary> {
        Log.d("MessageRepository", "Pasa por getChatsForUser")
        val chats = mutableListOf<ChatSummary>()

        val chatsRef = database.getReference("chats")

        try {
            // 1. Obtener todos los chats
            val chatsSnapshot = chatsRef.get().await()

            for (chatSnapshot in chatsSnapshot.children) {
                val chatId = chatSnapshot.key
                val participantes = chatSnapshot.child("participantes").getValue<List<String>>()

                // 2. Verificar si el usuario participa en el chat y si está abierto
                if (participantes?.contains(userId) == true &&
                    chatSnapshot.child("estado").getValue(String::class.java) == "Abierto"
                ) {

                    val titulo = chatSnapshot.child("titulo").getValue(String::class.java)
                    val fecha = chatSnapshot.child("fecha").getValue(Long::class.java) ?: 0L

                    chats.add(ChatSummary(chatId, titulo!!, fecha))
                }
            }

            // 3. Ordenar los chats por fecha de mayor a menor
            chats.sortByDescending { it.dateChat }

        } catch (e: Exception) {
            // Manejo de errores
            println("Error al obtener chats: ${e.message}")
        }

        return chats
    }

    companion object {
        private const val TAG = "MessageRepository"
        private const val CHATS_COLLECTION = "chats"
        private const val MESSAGES_COLLECTION = "mensajes"
        private const val MEMBERS_COLLECTION = "participantes"
    }
}
