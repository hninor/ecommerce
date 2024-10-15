package com.example.marketplacepuj.ui.features.messages.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplacepuj.databinding.ItemMessageBinding
import com.example.marketplacepuj.ui.features.messages.data.model.ChatSummary
import com.example.marketplacepuj.ui.features.messages.data.model.Message
import com.example.marketplacepuj.ui.features.messages.viewmodel.ItemMessageViewModel

class MessageAdapter(
    private var chatsSummary: List<ChatSummary>,
    private val onMessageClick: (String) -> Unit
) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatsSummary.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = chatsSummary[position]
        holder.bind(ItemMessageViewModel(message))
        holder.itemView.setOnClickListener {
            onMessageClick(message.chatId!!)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<ChatSummary>) {
        chatsSummary = newMessages
        this.notifyDataSetChanged()
    }
}

class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(viewModel: ItemMessageViewModel) {
        binding.viewmodel = viewModel
        binding.executePendingBindings() // Asegura que los cambios se reflejen en la vista
    }
}
