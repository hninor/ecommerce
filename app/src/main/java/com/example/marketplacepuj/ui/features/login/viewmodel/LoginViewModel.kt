package com.example.marketplacepuj.ui.features.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit
    var navController: NavController // Para almacenar el NavController

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onLoginClick(email: String, password: String) {
        // Realiza la autenticación con Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LoginViewModel", "Inicio de sesión exitoso")

                    // Actualizar el token FCM del usuario
                    updateFcmToken()

                    // Notificar al Fragment que el inicio de sesión fue exitoso
                    _isLoggedIn.value = true
                } else {
                    Log.e("LoginViewModel", "Error al iniciar sesión", task.exception)
                    // Notificar al Fragment que el inicio de sesión falló (puedes usar otro LiveData o un SingleLiveEvent)
                    _isLoggedIn.value = false
                    // Mostrar mensaje de error al usuario (puedes hacerlo desde el Fragment)
                }
            }
    }

    private fun updateFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result

                val userId = auth.currentUser?.uid // Obtener el ID del usuario actual

                if (userId != null) {
                    val userRef = database.getReference("usuarios/$userId")
                    userRef.child("fcmToken").setValue(token)
                        .addOnSuccessListener {
                            Log.d("LoginViewModel", "Token FCM actualizado exitosamente")
                        }
                        .addOnFailureListener { e ->
                            Log.e("LoginViewModel", "Error al actualizar el token FCM", e)
                        }
                }
            } else {
                Log.e("LoginViewModel", "Error al obtener el token FCM", task.exception)
            }
        }
    }
}
