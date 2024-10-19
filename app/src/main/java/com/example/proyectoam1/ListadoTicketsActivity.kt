package com.example.proyectoam1

import android.content.Intent
import android.os.Bundle
import android.util.Log // Importamos la clase Log para usarla en el Logcat
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoam1.adaptador.ticketAdapter
import com.example.proyectoam1.clases.Producto
import com.example.proyectoam1.clases.ProductoResponse
import com.example.proyectoam1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoTicketsActivity : AppCompatActivity(), ticketAdapter.OnProductoClickListener {
    private lateinit var btnVolver: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ticketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listado_mis_tickets)

        // Inicializamos el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewProductos)
        btnVolver = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this, OpcionesMenu::class.java)
            startActivity(intent) // Abre la actividad ListadoUsuario
        }
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargamos los productos desde la API
        cargarListaProductos()
    }

    private fun cargarListaProductos() {
        // Llamamos a la API para obtener la lista de productos
        val call = RetrofitClient.apiService.obtenerListaProductos()

        call.enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                if (response.isSuccessful) {
                    // Obtenemos el objeto ProductoResponse
                    val productoResponse = response.body()

                    // Extraemos la lista de productos desde ProductoResponse
                    val listaProductos = productoResponse?.response ?: emptyList() // Cambiado a 'response'
                    Log.d("ListadoTicketsActivity", "Productos obtenidos: ${listaProductos.size} productos")

                    productoAdapter = ticketAdapter(listaProductos, this@ListadoTicketsActivity)
                    recyclerView.adapter = productoAdapter
                } else {
                    // Mostramos un mensaje y log del error si la respuesta no fue exitosa
                    val errorMsg = "Error al obtener productos: ${response.code()} - ${response.message()}"
                    Toast.makeText(this@ListadoTicketsActivity, errorMsg, Toast.LENGTH_LONG).show()
                    Log.e("ListadoTicketsActivity", errorMsg)
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                // Mostramos un mensaje y registramos el error en Logcat si la llamada falló
                val errorMsg = "Error en la llamada API: ${t.message}"
                Toast.makeText(this@ListadoTicketsActivity, errorMsg, Toast.LENGTH_LONG).show()
                Log.e("ListadoTicketsActivity", errorMsg, t) // Incluye el stack trace para mayor detalle
            }
        })
    }

    // Implementación del método de la interfaz
    override fun onProductoClick(producto: Producto) {
        val intent = Intent(this, ActualizarTicket::class.java)
        intent.putExtra("PRODUCTO_ID", producto.idProducto)  // Pasar el ID del producto
        startActivity(intent)  // Iniciar la actividad
        // Aquí puedes iniciar otra actividad o hacer lo que necesites
    }
}
