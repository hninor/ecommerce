package com.example.marketplacepuj.ui.features.messages.data.repository.remote

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("sendNotificationToUser") // Reemplaza con el nombre de tu Cloud Function
    fun sendNotification(@Body request: NotificationRequest): Call<ResponseBody>
}

const val TAG = "NotificationService"

data class NotificationRequest(val recipientId: String, val message: String)

// ...

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://us-central1-marketplacepuj.cloudfunctions.net/") // Reemplaza con la URL de tu Cloud Function
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service = retrofit.create(NotificationService::class.java)

val recipientId = "el_id_del_usuario_android" // Reemplaza con el ID del usuario en tu base de datos
val message = "Hola desde Android!"
val request = NotificationRequest(recipientId, message)

val callback = object : Callback<ResponseBody> {
    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            Log.d(TAG,
            "Notificación enviada exitosamente")
        } else {
            Log.e(TAG, "Error al enviar la notificación: ${response.errorBody()?.string()}")
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        Log.e(TAG, "Error al enviar la notificación", t)
    }
}

fun sendNotification(adminId: String, messageText: String) {
    val request = NotificationRequest(adminId, messageText) // Crea la solicitud con los nuevos parámetros
    service.sendNotification(request).enqueue(callback)
}

