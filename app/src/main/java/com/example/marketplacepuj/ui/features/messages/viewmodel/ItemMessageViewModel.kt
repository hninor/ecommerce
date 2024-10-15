package com.example.marketplacepuj.ui.features.messages.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketplacepuj.ui.features.messages.data.model.ChatSummary

class ItemMessageViewModel (private val chatSummary: ChatSummary) : ViewModel() {

    val title:String
        get() = chatSummary.titleChat

    val dateChat: Long
        get() = chatSummary.dateChat
}