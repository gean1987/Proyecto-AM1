package com.example.proyectoam1.network

import com.example.proyectoam1.clases.Producto
import com.example.proyectoam1.clases.ProductoResponse
import com.example.proyectoam1.clases.ProductoIndividualResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


    // Obtener producto por ID
    interface ApiService {
        // Obtener producto por ID
        @GET("api/Producto/Obtener/{id}")
        fun obtenerProducto(@Path("id") id: Int): Call<ProductoIndividualResponse>

        // Obtener lista de productos
        @GET("api/Producto/Lista")
        fun obtenerListaProductos(): Call<ProductoResponse> // Esto sigue igual

        // Guardar un nuevo producto
        @POST("api/Producto/Guardar")
        fun guardarProducto(@Body producto: Producto): Call<ProductoResponse>

        // Editar un producto existente
        @PUT("api/Producto/Editar")
        fun editarProducto(@Body producto: Producto): Call<ProductoResponse>

        // Eliminar un producto por ID
        @DELETE("api/Producto/Eliminar/{idProducto}")
        fun eliminarProducto(@Path("idProducto") idProducto: Int): Call<Void>
    }


