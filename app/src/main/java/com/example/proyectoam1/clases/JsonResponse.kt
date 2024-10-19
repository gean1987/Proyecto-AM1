package com.example.proyectoam1.clases

// Clase que representa el producto en la respuesta
data class Producto(
    val idProducto: Int,
    val codigoBarra: String,
    val nombre: String,
    val marca: String,
    val categoria: String,
    val precio: Double
)

// Clase que representa la estructura completa de la respuesta
data class ProductoResponse(
    val mensaje: String,
    val response: List<Producto>
)


data class ProductoIndividualResponse(
    val mensaje: String,
    val response: Producto // En este caso, `response` es un objeto de tipo Producto
)
