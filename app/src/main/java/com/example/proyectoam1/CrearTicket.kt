package com.example.proyectoam1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoam1.clases.Producto
import com.example.proyectoam1.clases.ProductoResponse
import com.example.proyectoam1.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearTicket : AppCompatActivity() {

    private lateinit var etCodigoBarra: TextInputEditText
    private lateinit var etNombre: TextInputEditText
    private lateinit var etMarca: TextInputEditText
    private lateinit var etCategoria: TextInputEditText
    private lateinit var etPrecio: TextInputEditText
    private lateinit var btnCrearProducto: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crearticket)

        etCodigoBarra = findViewById(R.id.etCodigoBarra)
        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etCategoria = findViewById(R.id.etCategoria)
        etPrecio = findViewById(R.id.etPrecio)
        btnCrearProducto = findViewById(R.id.btnCrearProducto)
        btnVolver = findViewById(R.id.btnVolver)

        btnVolver.setOnClickListener {
            val intent = Intent(this, OpcionesMenu::class.java)
            startActivity(intent) // Abre la actividad ListadoUsuario
        }
        // Ajuste del padding al detectar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Evento de click para crear producto
        btnCrearProducto.setOnClickListener {
            val codigoBarra = etCodigoBarra.text.toString()
            val nombre = etNombre.text.toString()
            val marca = etMarca.text.toString()
            val categoria = etCategoria.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull()


            if (codigoBarra.isNotEmpty() && nombre.isNotEmpty() && marca.isNotEmpty() && categoria.isNotEmpty() && precio != null) {
                // Crear el producto y llamar a la API
                val nuevoProducto = Producto(0, codigoBarra, nombre, marca, categoria, precio)
                guardarProducto(nuevoProducto)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Función para llamar a la API y guardar el producto
    private fun guardarProducto(producto: Producto) {
        val call = RetrofitClient.apiService.guardarProducto(producto)

        // Ahora el tipo de la respuesta debe ser ProductoResponse
        call.enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CrearTicket, "Producto creado con éxito", Toast.LENGTH_LONG).show()
                     finish()  // Cerrar la actividad al terminar
                } else {
                    Toast.makeText(this@CrearTicket, "Error al crear producto", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                Toast.makeText(this@CrearTicket, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
