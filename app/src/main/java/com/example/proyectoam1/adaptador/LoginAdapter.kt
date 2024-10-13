package com.example.proyectoam1.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoam1.R
import com.example.proyectoam1.UsuarioActualizarActivity
import com.example.proyectoam1.clases.Usuario
import com.example.proyectoam1.utils.AppConfig

class LoginAdapter(var data:ArrayList<Usuario>):RecyclerView.Adapter<VistaLogin>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaLogin {
        //convertir en View el archivo item_docente.xml
        var item=LayoutInflater.from(parent.context).inflate(R.layout.item_usuario,parent,false)
        return VistaLogin(item)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VistaLogin, position: Int) {
        // Mostrar datos
        holder.tvCodigo.setText(data.get(position).codigo.toString())
        holder.tvNombre.setText(data.get(position).nombre)
        holder.tvPaterno.setText(data.get(position).paterno)
        holder.tvMaterno.setText(data.get(position).materno)

        // Manejar click en item
        holder.itemView.setOnClickListener {
            val intent = Intent(AppConfig.CONTEXTO,UsuarioActualizarActivity::class.java)
            // Pasar el código al intent
            intent.putExtra("codigo", data.get(position).codigo ?: 0)
            // Lanzar la actividad
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

}