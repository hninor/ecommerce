package com.example.marketplacepuj.ui.features.catalogo.viewmodel


import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.marketplacepuj.ui.features.catalogo.data.Product
import com.example.marketplacepuj.ui.features.catalogo.screens.CartItem
import com.example.marketplacepuj.ui.features.catalogo.screens.Category
import com.example.marketplacepuj.ui.features.catalogo.screens.Subcategory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date


fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    return format.format(this)
}

data class DetallePedido(
    val cantidad: Int,
    val descuento: Int,
    val idProducto: String,
    val monto: Int,
    val precioTotalProducto: Int
)

data class Pedido(
    val detallePedido: List<DetallePedido>,
    val estado: String,
    val fechaOrden: String,
    val idOrden: Long,
    val idPedido: Long,
    val idUsuario: String,
    val impuestos: Int,
    val precioTotal: Int,
    val valorNeto: Int
)

class CatalogueViewModel : ViewModel() {
    val categories = mutableStateListOf<Category>()
    val cartItems = mutableStateListOf<CartItem>()
    val products = mutableListOf<Product>()
    val selectedCategory = mutableStateOf("")

    private val database = Firebase.database
    private val productsRef = database.getReference("productos")
    val pedidosRef = database.getReference("pedidos")

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                categories.clear()
                categories.addAll(getCategories())
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores
            }
        })
    }

    fun getCategories(): List<Category> {
        val response = mutableListOf<Category>()
        val agrupadosCategoria = products.groupBy { it.categoria }
        var count = 0
        agrupadosCategoria.forEach {
            response.add(Category(++count, it.key, getSubcategories(it.value)))
        }
        return response
    }

    fun filterByCategory(nombreCategoria: String) {
        categories.clear()
        if (selectedCategory.value == nombreCategoria) {
            selectedCategory.value = ""
            categories.addAll(getCategories())
        } else {
            selectedCategory.value = nombreCategoria
            categories.addAll(getCategories().filter { it.name == nombreCategoria })
        }

    }

    private fun getSubcategories(products: List<Product>): List<Subcategory> {
        val response = mutableListOf<Subcategory>()
        val agrupadosSubcategoria = products.groupBy { it.subcategoria }
        var count = 0
        agrupadosSubcategoria.forEach {
            response.add(
                Subcategory(
                    ++count,
                    it.key,
                    it.value.map {
                        com.example.marketplacepuj.ui.features.catalogo.screens.Product(
                            id = it.idProducto,
                            name = it.nombre,
                            category = it.categoria,
                            subCategory = it.subcategoria,
                            imageUrl = it.url_imagen,
                            price = it.precio.toDouble(),
                            description = it.descripcion
                        )
                    })
            )
        }

        return response
    }

    fun getProductDetail(productId: String?): com.example.marketplacepuj.ui.features.catalogo.screens.Product? {
        var response: com.example.marketplacepuj.ui.features.catalogo.screens.Product? = null
        val foundProduct = products.find { it.idProducto == productId }
        if (foundProduct != null) {
            response = com.example.marketplacepuj.ui.features.catalogo.screens.Product(
                id = foundProduct.idProducto,
                name = foundProduct.nombre,
                category = foundProduct.categoria,
                subCategory = foundProduct.subcategoria,
                imageUrl = foundProduct.url_imagen,
                price = foundProduct.precio.toDouble(),
                description = foundProduct.descripcion
            )
        }
        return response
    }


    fun addToCartItem(product: com.example.marketplacepuj.ui.features.catalogo.screens.Product) {
        cartItems.add(CartItem(product.name, product.price, product.imageUrl))
    }

    fun onDeleteCartItem(it: CartItem) {
        cartItems.remove(it)
    }

    fun crearPedido() {

        val total = cartItems.sumOf { it.price }

        val pedido = Pedido(
            detallePedido = obtenerDetalle(),
            estado = "pendiente",
            fechaOrden = Date().toSimpleString(),
            idOrden = 968044891,
            idPedido = 968044891,
            idUsuario = "V7lOl23jscQ9LG9tLDjPcJWF2cp1",
            impuestos = 0,
            precioTotal = total.toInt(),
            valorNeto = total.toInt()
        )


        pedidosRef.push().setValue(pedido)
            .addOnSuccessListener {
                Log.d("Firebase", "Datos escritos correctamente")
                cartItems.clear()
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Error al escribir datos: ${error.message}")
            }
    }

    private fun obtenerDetalle(): List<DetallePedido> {
        val response = mutableListOf<DetallePedido>()

        cartItems.forEach {
            response.add(
                DetallePedido(
                    cantidad = 2,
                    descuento = 0,
                    idProducto = it.name,
                    monto = it.price.toInt(),
                    precioTotalProducto = it.price.toInt()
                )
            )

        }

        return response


    }
}