package com.example.proyectoam1.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectoam1.utils.AppConfig

class InitBD : SQLiteOpenHelper(AppConfig.CONTEXTO, "proyectoAM1.bd", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE tb_usuario (
                cod INTEGER PRIMARY KEY AUTOINCREMENT, 
                nom VARCHAR(35), 
                pat VARCHAR(35), 
                mat VARCHAR(35), 
                sexo VARCHAR(15), 
                usuario VARCHAR(15) unique,
                contrasena VARCHAR(255)  
            )
        """.trimIndent())

        val initialUser = ContentValues().apply {
            put("nom", "Gean")
            put("pat", "Villafuerte")
            put("mat", "Flores")
            put("sexo", "Masculino")
            put("usuario", "gean123")
            put("contrasena", "123")
        }
        db.insert("tb_usuario", null, initialUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tb_usuario")
        onCreate(db)
    }
}
