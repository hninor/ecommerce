package com.example.marketplacepuj.ui.features.messages.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository
import javax.inject.Provider


class MessageViewModelFactory(private val repositoryProvider: Provider<MessageRepository>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(repositoryProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}