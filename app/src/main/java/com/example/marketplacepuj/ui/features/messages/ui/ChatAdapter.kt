package com.example.marketplacepuj.ui.features.messages.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplacepuj.databinding.ItemMessageChatBinding
import com.example.marketplacepuj.ui.features.messages.data.model.Chat
import com.example.marketplacepuj.ui.features.messages.data.model.Message

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ChatViewHolder>(DiffCallback) {

    class ChatViewHolder(private var binding: ItemMessageChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Chat) {
            binding.messagechat = message
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem === newItem // Verifica si son el mismo objeto en memoria
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.idRemitente == newItem.idRemitente && oldItem.mensaje == newItem.mensaje
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ItemMessageChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }
}