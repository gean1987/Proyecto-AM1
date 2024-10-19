package com.example.proyectoam1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoam1.clases.Usuario
import com.example.proyectoam1.controller.UsuarioController
import com.google.android.material.textfield.TextInputEditText

class UsuarioActualizarActivity : AppCompatActivity() {
    private lateinit var txtCodigo:TextInputEditText
    private lateinit var txtNombre:TextInputEditText
    private lateinit var txtPaterno:TextInputEditText
    private lateinit var txtMaterno:TextInputEditText
    private lateinit var txtUsuario:TextInputEditText
    private lateinit var txtContrasena:TextInputEditText
    private lateinit var spnSexo:AutoCompleteTextView
    private lateinit var btnGrabar:Button
    private lateinit var btnCerrar:Button
    private lateinit var btnEliminar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_actualizar_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigo=findViewById(R.id.txtCodigoActualizar)
        txtNombre=findViewById(R.id.txtNombreActualizar)
        txtPaterno=findViewById(R.id.txtPaternoActualizar)
        txtMaterno=findViewById(R.id.txtMaternoActualizar)
        txtContrasena =findViewById(R.id.txtcontrasenaActualizar)
        txtUsuario=findViewById(R.id.txtusuarioActualizar)
        spnSexo=findViewById(R.id.spnSexoActualizar)
        btnGrabar=findViewById(R.id.btnActualizarDocente)
        btnEliminar=findViewById(R.id.btnEliminarDocente)
        btnCerrar=findViewById(R.id.btnVolver)
        btnGrabar.setOnClickListener { grabar() }
        btnCerrar.setOnClickListener { cerrar() }
        btnEliminar.setOnClickListener { eliminar() }
        datos()
    }
    fun eliminar(){
        var cod=txtCodigo.text.toString().toInt()
        showAlertConfirm("Seguro de eliminar Docente con ID : "+cod,cod)
    }
    fun grabar(){
        //leer controles
        var cod=txtCodigo.text.toString().toInt()
        var nom=txtNombre.text.toString()
        var pat=txtPaterno.text.toString()
        var mat=txtMaterno.text.toString()
        var usu=txtUsuario.text.toString()
        var contra=txtContrasena.text.toString()

        var sexo=spnSexo.text.toString()
        //crear objeto
        var bean=Usuario(cod,nom,pat,mat,sexo,usu,contra)
        //invocar mètodo update
        var salida=UsuarioController().update(bean)
        if(salida>0)
            showAlert("Usuario Actualizado")
        else
            showAlert("Error en la actualizaciòn")
    }
    fun cerrar(){
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    fun showAlertConfirm(men:String,cod:Int){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                                    dialogInterface: DialogInterface, i: Int ->
           var salida=UsuarioController().delete(cod)
            if(salida>0)
                showAlert("Usuario Eliminado")
            else
                showAlert("Error en la eliminación")
        })
        builder.setNegativeButton  ("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun datos() {
        try {
            var info = intent.extras
            if (info != null) {
                val codigo = info.getInt("codigo")
                var bean = UsuarioController().findById(codigo)
                if (bean != null) {
                    txtCodigo.setText(bean.codigo.toString())
                    txtNombre.setText(bean.nombre)
                    txtPaterno.setText(bean.paterno)
                    txtMaterno.setText(bean.materno)
                    txtUsuario.setText(bean.usuario.toString())
                    txtContrasena.setText(bean.contrasena.toString())
                    spnSexo.setText(bean.sexo, false)
                } else {
                    showAlert("No se encontró el usuario con código $codigo")
                }
            } else {
                showAlert("No se recibieron datos del usuario")
            }
        } catch (e: Exception) {
            Log.e("UsuarioActualizar", "Error al obtener datos del usuario", e)
            showAlert("Error al cargar los datos del usuario.")
        }
    }


}