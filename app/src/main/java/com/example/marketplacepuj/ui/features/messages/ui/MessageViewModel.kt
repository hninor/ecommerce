package com.example.marketplacepuj.ui.features.messages.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplacepuj.ui.features.messages.data.model.ChatSummary
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Provider

class MessageViewModel(private val repositoryProvider: Provider<MessageRepository>) : ViewModel() {
    private val _messages = MutableLiveData<List<ChatSummary>>()

    val messages : LiveData<List<ChatSummary>> get() = _messages
    private val repository: MessageRepository by lazy { repositoryProvider.get() }
    init {
        getCurrentMessages()
    }

    private fun getCurrentMessages() {
        Log.d("MessageViewModel", "Pasa por getCurrentMessages")
        viewModelScope.launch(Dispatchers.IO) {
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            val userId = auth.currentUser?.uid
            val messagesCurrent = repository.getChatsForUser(userId!!)
            withContext(Dispatchers.Main) {
                _messages.value = messagesCurrent
            }
        }
    }
}