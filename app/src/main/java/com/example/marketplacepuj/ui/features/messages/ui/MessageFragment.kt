package com.example.marketplacepuj.ui.features.messages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplacepuj.databinding.FragmentMessageBinding
import com.example.marketplacepuj.ui.features.messages.data.repository.local.MessageRepository


class MessageFragment : Fragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    private lateinit var binding: FragmentMessageBinding
    private val viewModel: MessageViewModel by viewModels {
        MessageViewModelFactory { MessageRepository() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MessageAdapter(emptyList()) { chatId ->
            val action = MessageFragmentDirections.actionMessageFragmentToChatDialogFragment(chatId)
            findNavController().navigate(action) // Usar el NavController del fragmento
        }
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(context)

        // Observar los cambios en el LiveData messages solo si es la primera vez que se crea la vista
        if (savedInstanceState == null) {
            viewModel.messages.observe(viewLifecycleOwner) { messages ->
                adapter.updateMessages(messages)
            }
        }
    }
}