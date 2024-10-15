package com.example.marketplacepuj.ui.features.messages.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository
import com.example.marketplacepuj.ui.features.messages.viewmodel.ChatViewModel
import javax.inject.Provider

class ChatViewModelFactory(private val repositoryProvider: Provider<MessageRepository>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repositoryProvider.get()) as T // Pasa el repositorio
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}