package com.example.proyectoam1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OpcionesMenu : AppCompatActivity() {
    private lateinit var btnUsuario: ImageView
    private lateinit var btnListarTicket: ImageView
    private lateinit var btnCrearTicket: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Extiende el contenido a los bordes de la pantalla
        setContentView(R.layout.opciones_menu)

        // Manejar los márgenes según los recortes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener la referencia al ImageView
        btnUsuario = findViewById(R.id.btnUsuario)
        btnListarTicket = findViewById(R.id.imagen_btn4)
        btnCrearTicket = findViewById(R.id.imagen_btn2)

        // Agregar el listener al "botón" de usuario (ImageView)
        btnUsuario.setOnClickListener {
            val intent = Intent(this, ListadoUsuario::class.java)
            startActivity(intent) // Abre la actividad ListadoUsuario
        }
        //boton ticket
        btnListarTicket.setOnClickListener {
            val intent = Intent(this, ListadoTicket::class.java)
            startActivity(intent) // Abre la actividad ListadoUsuario
        }
        btnCrearTicket.setOnClickListener {
            val intent = Intent(this, ListadoTicketsActivity::class.java)
            startActivity(intent) // Abre la actividad ListadoUsuario
        }


    }
}
