package com.example.proyectoam1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OpcionesMenu : AppCompatActivity() {
    private lateinit var btnUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Permitir que el contenido se extienda hasta los bordes de la pantalla
        setContentView(R.layout.opciones_menu) // Establecer el layout de la actividad

        // Aplicar márgenes según los recortes de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Establecer el padding del layout principal según los insets del sistema
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Retornar los insets para su uso posterior
        }
        btnUsuario = findViewById(R.id.btnUsuario)

        btnUsuario.setOnClickListener {
            val intent = Intent(this, ListadoUsuario::class.java)
            startActivity(intent)
        }

    }
}
