package com.example.marketplacepuj.ui.features.catalogo.viewmodel


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplacepuj.ui.features.catalogo.screens.OrderItem
import com.example.marketplacepuj.ui.features.catalogo.screens.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date


class Calificacion(
    var calificacion: Int = 0,
    var fechaCalificacion: String = "",
    var idCalificacion: String = "",
    var idPedido: String = "",
    var idProducto: String = "",
    var resena: String = "",
    var usuario: String = ""
)

class PedidoViewModel : ViewModel() {
    val pedidos = mutableListOf<Pedido>()

    val orderItems = mutableStateListOf<OrderItem>()


    private val database = Firebase.database
    val pedidosRef = database.getReference("pedidos")
    val calificacionesRef = database.getReference("calificaciones")


    init {

        obtenerPedidos()


    }


    fun obtenerPedidos() {
        pedidosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                viewModelScope.launch(Dispatchers.IO) {
                    pedidos.clear()

                    for (pedidoSnapshot in snapshot.children) {
                        val pedido = pedidoSnapshot.getValue(Pedido::class.java)
                        if (pedido != null) {
                            pedidos.add(pedido)
                        }
                    }

                    orderItems.clear()
                    orderItems.addAll(getOrderItems(pedidos).reversed())
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private suspend fun getOrderItems(pedidos: MutableList<Pedido>): List<OrderItem> {
        val response = mutableListOf<OrderItem>()
        pedidos.forEach {
            response.add(
                OrderItem(
                    "Pedido",
                    it.precioTotal.toDouble(),
                    getListaProductos(it),
                    it.fechaOrden.toDate(),
                    it.idPedido
                )
            )
        }

        return response

    }

    private suspend fun getListaProductos(pedido: Pedido): List<Product> {
        val response =
            mutableListOf<Product>()
        pedido.detallePedido.forEach {
            response.add(
                Product(
                    id = it.idProducto,
                    name = it.nombreProducto,
                    category = "",
                    subCategory = "",
                    imageUrl = it.urlImagen,
                    price = it.monto.toDouble(),
                    description = "",
                    rating = getRating(it.idProducto, pedido.idPedido)
                )
            )

        }

        return response

    }

    private suspend fun getRating(idProducto: String, idPedido: String): MutableState<Int> {
        var response = mutableStateOf(0)

        val dataSnapshot =
            calificacionesRef.orderByChild("idPedido").equalTo(idPedido).get().await()

        val calificaciones = mutableListOf<Calificacion>()
        for (calificacionSnapshot in dataSnapshot.children) {
            val calificacion = calificacionSnapshot.getValue(Calificacion::class.java)
            if (calificacion != null) {
                calificaciones.add(calificacion)
            }
        }

        val calificacionesPedido = calificaciones.filter { it.idProducto == idProducto }
        if (calificacionesPedido.isNotEmpty()) {
            response = mutableStateOf(calificacionesPedido[0].calificacion)

        }

        return response

    }


    fun onRatingChanged(idPedido: String, idProducto: String, rating: Int) {
        val order = orderItems.find { it.id == idPedido }
        if (order != null) {
            val producto = order.productos.find { it.id == idProducto }
            if (producto != null) {
                producto.rating.value = rating
                onWriteRealtimeDatabase(idPedido, idProducto, rating)
            }
        }


    }

    private fun onWriteRealtimeDatabase(idPedido: String, idProducto: String, rating: Int) {
        calificacionesRef.orderByChild("idPedido").equalTo(idPedido).get()
            .addOnSuccessListener {
                val calificaciones = mutableListOf<Calificacion>()
                for (calificacionSnapshot in it.children) {
                    val calificacion = calificacionSnapshot.getValue(Calificacion::class.java)
                    if (calificacion != null) {
                        calificaciones.add(calificacion)
                    }
                }

                val calificacionesPedido = calificaciones.filter { it.idProducto == idProducto }
                if (calificacionesPedido.isEmpty()) {

                    val nuevaCalificacion = Calificacion(
                        calificacion = rating,
                        fechaCalificacion = Date().toSimpleString(),
                        idPedido = idPedido,
                        idProducto = idProducto,
                        resena = "",
                        usuario = "U456789123"
                    )

                    escribirCalificacion(nuevaCalificacion)

                } else {

                    actualizarCalificacion(calificacionesPedido[0].idCalificacion, rating)
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
    }


    fun escribirCalificacion(calificacion: Calificacion) {
        val newPostRef = calificacionesRef.push()
        calificacion.idCalificacion =
            newPostRef.key.toString() // Asigna automáticamente un ID único

        newPostRef.setValue(calificacion)
            .addOnSuccessListener {
                Log.d("Firebase", "Escritura exitosa")
            }
            .addOnFailureListener {
                Log.e("Firebase", "Error al escribir: ${it.message}")
            }
    }

    fun actualizarCalificacion(idCalificacion: String, rating: Int) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("calificaciones/$idCalificacion")

        val updates = HashMap<String, Any>()
        updates["calificacion"] = rating
        updates["fechaCalificacion"] =
            Date().toSimpleString() // Puedes actualizar otros campos según sea necesario

        myRef.updateChildren(updates)
            .addOnSuccessListener {
                Log.d("Firebase", "Calificación actualizada exitosamente")
            }
            .addOnFailureListener {
                Log.e("Firebase", "Error al actualizar la calificación: ${it.message}")
            }
    }

    fun getProductos(orderId: String): List<Product> {
        val response = mutableListOf<Product>()
        val order = orderItems.find { it.id == orderId }
        if (order != null) {
            response.addAll(order.productos)
        }
        return response
    }

    fun getFechaCompra(orderId: String): Date {
        val order = orderItems.find { it.id == orderId }
        if (order != null) {
            return order.fecha
        }
        return Date()
    }


}

private fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    return format.parse(this)
}
