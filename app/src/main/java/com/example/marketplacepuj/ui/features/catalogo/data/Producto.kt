package com.example.marketplacepuj.ui.features.catalogo.data

data class Product(
    val idProducto: String = "",
    val activo: Int = 1,
    val cantidadDisponible: Int = 1,
    val categoria: String = "",
    val creadoPor: String = "",
    val descripcion: String = "",
    val nombre: String = "",
    val precio: Int = 0,
    val subcategoria: String = "",
    val url_imagen: String = ""
)
