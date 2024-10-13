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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoam1.adaptador.LoginAdapter
import com.example.proyectoam1.clases.Usuario
import com.example.proyectoam1.controller.UsuarioController
import com.google.android.material.textfield.TextInputEditText
class ListadoUsuario : AppCompatActivity() {
    private lateinit var btnNuevo: Button
    private lateinit var rvDocentes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listadousuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //
        //
        btnNuevo=findViewById(R.id.btnNuevoDocente)
        rvDocentes=findViewById(R.id.rvDocentes)
        //crear objeto de la clase DocenteAdapter
        var adaptador=LoginAdapter(UsuarioController().findAll())
        //estilo del RecyclerView
        rvDocentes.layoutManager= LinearLayoutManager(this)//,LinearLayoutManager.HORIZONTAL,false)
        //enviar el objeto adaptador dentro de rvDocentes
        rvDocentes.adapter=adaptador
        //
        btnNuevo.setOnClickListener {
            var intent=Intent(this,UsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}