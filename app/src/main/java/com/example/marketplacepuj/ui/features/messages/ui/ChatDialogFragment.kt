package com.example.marketplacepuj.ui.features.messages.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplacepuj.databinding.FragmentChatDialogBinding
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository
import com.example.marketplacepuj.ui.features.messages.viewmodel.ChatViewModel
import javax.inject.Provider

class ChatDialogFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(Provider { MessageRepository() }) // Usa la fábrica
    }
    private lateinit var binding: FragmentChatDialogBinding
    private val args by navArgs<ChatDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el chatId de los argumentos del fragmento
        val chatId = args.chatId
        Log.d("ChatDialogFragment", "Chat ID: $chatId")

        // Configurar el ViewModel y observar los cambios
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadChat(chatId)

        // Configurar el RecyclerView
        val adapter = ChatAdapter()
        binding.rvMessagesChat.adapter = adapter
        binding.rvMessagesChat.layoutManager = LinearLayoutManager(context)
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }


        // Configurar el botón para enviar mensajes
        binding.btnSendMessage.setOnClickListener {
            val messageText = binding.etMessageChat.text.toString()
            if (messageText.isNotBlank()) {
                viewModel.sendMessage(
                    messageText,
                    chatId
                ) // Envía el mensaje usando el ViewModel con el chatId
                binding.etMessageChat.text.clear() // Limpia el campo de texto
                viewModel.loadChat(chatId)
            }
        }
    }

}