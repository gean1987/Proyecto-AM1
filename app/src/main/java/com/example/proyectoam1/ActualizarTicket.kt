package com.example.proyectoam1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoam1.clases.Producto
import com.example.proyectoam1.clases.ProductoResponse
import com.example.proyectoam1.clases.ProductoIndividualResponse
import com.example.proyectoam1.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActualizarTicket : AppCompatActivity() {

    private lateinit var etCodigoBarra: TextInputEditText
    private lateinit var etNombre: TextInputEditText
    private lateinit var etMarca: TextInputEditText
    private lateinit var etCategoria: TextInputEditText
    private lateinit var etPrecio: TextInputEditText
    private lateinit var btnActualizarProducto: Button
    private var productoId: Int = 0  // Para guardar el ID del producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ticket_actualizar)

        // Inicializar vistas
        etCodigoBarra = findViewById(R.id.etCodigoBarra)
        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etCategoria = findViewById(R.id.etCategoria)
        etPrecio = findViewById(R.id.etPrecio)
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto)

        // Ajuste del padding según los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir datos del Intent
        productoId = intent.getIntExtra("PRODUCTO_ID", 0)  // Obtener el ID del producto desde el intent
        cargarProducto(productoId)  // Cargar los datos del producto utilizando el ID

        // Manejo del evento click para actualizar producto
        btnActualizarProducto.setOnClickListener {
            val codigoBarra = etCodigoBarra.text.toString()
            val nombre = etNombre.text.toString()
            val marca = etMarca.text.toString()
            val categoria = etCategoria.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull()

            if (codigoBarra.isNotEmpty() && nombre.isNotEmpty() && marca.isNotEmpty() && categoria.isNotEmpty() && precio != null) {
                val productoActualizado = Producto(productoId, codigoBarra, nombre, marca, categoria, precio)
                actualizarProducto(productoActualizado)
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos correctamente", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Método para cargar los datos del producto desde la API
    private fun cargarProducto(id: Int) {
        val call = RetrofitClient.apiService.obtenerProducto(id)
        call.enqueue(object : Callback<ProductoIndividualResponse> {
            override fun onResponse(call: Call<ProductoIndividualResponse>, response: Response<ProductoIndividualResponse>) {
                if (response.isSuccessful) {
                    val producto = response.body()?.response // Ahora `response` es un solo Producto

                    producto?.let {
                        // Rellenar los campos con los datos obtenidos del producto
                        etCodigoBarra.setText(it.codigoBarra)
                        etNombre.setText(it.nombre)
                        etMarca.setText(it.marca)
                        etCategoria.setText(it.categoria)
                        etPrecio.setText(it.precio.toString())
                    } ?: run {
                        Toast.makeText(this@ActualizarTicket, "Producto no encontrado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@ActualizarTicket, "Error al cargar producto: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProductoIndividualResponse>, t: Throwable) {
                Toast.makeText(this@ActualizarTicket, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Método para actualizar los datos del producto en la API
    private fun actualizarProducto(producto: Producto) {
        val call = RetrofitClient.apiService.editarProducto(producto)

        call.enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ActualizarTicket, "Producto actualizado con éxito", Toast.LENGTH_LONG).show()
                    finish()  // Finalizar la actividad y volver a la anterior
                } else {
                    Toast.makeText(this@ActualizarTicket, "Error al actualizar producto: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                Toast.makeText(this@ActualizarTicket, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
