package com.example.proyectoam1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoam1.controller.UsuarioController
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var btnNuevo: Button
    private lateinit var btnIngresar: Button
    private lateinit var txtUsuario: TextInputEditText
    private lateinit var txtContrasena: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtContrasena = findViewById(R.id.txtcontrasenaActualizar)
        txtUsuario = findViewById(R.id.txtusuarioActualizar)
        btnIngresar = findViewById(R.id.btn_ingresar)
        btnNuevo = findViewById(R.id.registerButton)

        btnIngresar.setOnClickListener { buscarUsuario() }

        btnNuevo.setOnClickListener {
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buscarUsuario() {
        // Leer controles
        val usu = txtUsuario.text.toString().trim()
        val contra = txtContrasena.text.toString().trim()

        // Validar que los campos no estén vacíos
        if (usu.isEmpty()) {
            Toast.makeText(this, "El campo de usuario no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        if (contra.isEmpty()) {
            Toast.makeText(this, "El campo de contraseña no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        // Llamada al método buscarUsuario() en el controlador
        val usuarioEncontrado = UsuarioController().buscarUsuario(usu, contra)

        // Validar si se encontró el usuario
        if (usuarioEncontrado != null) {
            val intent = Intent(this,OpcionesMenu::class.java)
            startActivity(intent)
        } else {
            showAlert("Error en las credenciales")
        }
    }

    private fun showAlert(men: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
