package com.example.marketplacepuj.ui.features.messages.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplacepuj.ui.features.messages.data.model.Chat
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.launch

class ChatViewModel(private val repository: MessageRepository) : ViewModel() {
    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat> get() = _chat

    private val _messages = MutableLiveData<List<Chat>>()
    val messages: LiveData<List<Chat>> get() = _messages

    private val _chatId = MutableLiveData<String>()
    val chatId: LiveData<String> get() = _chatId

    fun loadChat(chatId: String) {
        _chatId.value = chatId
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        viewModelScope.launch {
            _chat.value = repository.getChat(chatId) // Obtén el chat del repositorio
            _messages.value = repository.getMessagesForChat(chatId, userId!!) // Obtén los mensajes del chat
        }
    }

    fun sendMessage(messageText: String, chatId: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        viewModelScope.launch {
            _chat.value = repository.sendMessage(messageText, chatId, userId!!) // Enviará el mensaje al repositorio
        }
    }
}