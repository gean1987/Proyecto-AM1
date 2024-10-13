package com.example.proyectoam1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Definición de la clase
class ConexionSQL_Lite(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    // Método que se llama al crear la base de datos por primera vez
    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla
        db.execSQL("CREATE TABLE usuarios (usuario TEXT PRIMARY KEY, password TEXT)")

        // Valores por defecto para el usuario y contraseña
        val usuario = "admin"
        val password = "12345"

        // Insertar valores iniciales en la tabla admin3
        val datosAdmin = ContentValues()
        datosAdmin.put("usuario", usuario)
        datosAdmin.put("password", password)

        // Insertar los datos en la base de datos
        db.insert("usuarios", null, datosAdmin)
    }

    // Método que se llama cuando hay que actualizar la base de datos (no implementado en este caso)
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí se puede manejar la actualización de la base de datos si cambian las versiones
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }
}
