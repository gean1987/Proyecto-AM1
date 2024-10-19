package com.example.proyectoam1.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoam1.R
import com.example.proyectoam1.clases.Producto

class ticketAdapter(
    private val listaProductos: List<Producto>,
    private val onProductoClickListener: OnProductoClickListener  // Interfaz para el clic
) : RecyclerView.Adapter<ticketAdapter.ProductoViewHolder>() {

    interface OnProductoClickListener {
        fun onProductoClick(producto: Producto)  // Método que será implementado por la actividad
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvCategoriaProducto: TextView = itemView.findViewById(R.id.tvCategoriaProducto)
        val tvPrecioProducto: TextView = itemView.findViewById(R.id.tvPrecioProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.tvNombreProducto.text = producto.nombre
        holder.tvCategoriaProducto.text = producto.categoria
        holder.tvPrecioProducto.text = "Precio: ${producto.precio}"

        // Configurar clic en el item
        holder.itemView.setOnClickListener {
            onProductoClickListener.onProductoClick(producto)  // Llamada al listener
        }
    }

    override fun getItemCount() = listaProductos.size
}
