package com.example.marketplacepuj.ui.features.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.marketplacepuj.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de que este sea el layout correcto que contiene el NavHostFragment

        // Encuentra el NavHostFragment en tu layout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // No necesitas inicializar el ViewModel aquí, ya que se hará en el LoginFragment
    }
}