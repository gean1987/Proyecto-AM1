package com.example.proyectoam1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoam1.clases.ProductoIndividualResponse
import com.example.proyectoam1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoTicket : AppCompatActivity() {
    private lateinit var btnNuevo: Button
    private lateinit var txtProductoNombre: TextView  // Inicializar el TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listadoticket)

        // Ajustar padding para los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el TextView
        txtProductoNombre = findViewById(R.id.txtProductoNombre)

        btnNuevo = findViewById(R.id.btnNuevo)
        btnNuevo.setOnClickListener {
            obtenerProducto(2)  // Cambia el ID del producto según lo que desees
        }

        // Llamar a obtenerProducto con ID 1 al inicio
        obtenerProducto(1)
    }

    private fun obtenerProducto(id: Int) {
        val call = RetrofitClient.apiService.obtenerProducto(id)

        // Enviar la solicitud de manera asíncrona
        call.enqueue(object : Callback<ProductoIndividualResponse> {
            override fun onResponse(
                call: Call<ProductoIndividualResponse>,
                response: Response<ProductoIndividualResponse>
            ) {
                if (response.isSuccessful) {
                    val producto = response.body()?.response // Ahora se espera un solo producto
                    producto?.let {
                        // Actualizar el TextView con el nombre del producto
                        txtProductoNombre.text = "Producto: ${it.nombre}"  // Accediendo al producto
                    } ?: run {
                        Toast.makeText(this@ListadoTicket, "Producto no encontrado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@ListadoTicket, "Error en la respuesta", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProductoIndividualResponse>, t: Throwable) {
                Toast.makeText(this@ListadoTicket, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
