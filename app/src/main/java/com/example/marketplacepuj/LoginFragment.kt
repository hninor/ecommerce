package com.example.marketplacepuj

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.marketplacepuj.databinding.FragmentLoginBinding
import com.example.marketplacepuj.ui.features.login.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // Obtén el NavController desde el Fragment
        val navController = findNavController()

        // Configura la navegación en tu ViewModel
        viewModel.setNavController(navController)

        // Observa los cambios en el estado de inicio de sesión en el ViewModel (LiveData)
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                // Navega al MessageFragment si el inicio de sesión es exitoso
                navController.navigate(R.id.messageFragment)
            } else {
                // Muestra un mensaje de error si el inicio de sesión falla
                // ...
            }
        }

        // Configurar el OnClickListener del botón de login
        binding.btnLogin.setOnClickListener {
            val email = binding.etCorreo.text.toString() // Obtener el correo del EditText
            val password = binding.etPassword.text.toString() // Obtener la contraseña del EditText
            viewModel.onLoginClick(email, password) // Pasar los valores al ViewModel
        }
    }
}