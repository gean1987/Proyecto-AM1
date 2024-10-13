package com.example.proyectoam1.controller

import android.content.ContentValues
import com.example.proyectoam1.clases.Usuario
import com.example.proyectoam1.data.InitBD

class UsuarioController {
    //función que retorne un arreglo de objeto de la clase Docente
    fun findAll():ArrayList<Usuario>{
        var lista=ArrayList<Usuario>()
        //acceder a la base de datos en modo lectura
        var cn=InitBD().readableDatabase
        //sentencia
        var sql="select *from tb_usuario"
        var rs=cn.rawQuery(sql, null)
        //bucle
        while (rs.moveToNext()){
            var bean=Usuario(rs.getInt(0),rs.getString(1),
                            rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),
                            rs.getString(6))
            lista.add(bean)
        }
        return lista
    }
    //función para registrar Docente
    fun save(bean:Usuario):Int{
        var salida=-1
        //acceder a la bd en modo escritura
        var cn=InitBD().writableDatabase
        //crear objeto de la clase ContentValues
        var valores=ContentValues()
        // campos de la tabla tb_usuario)
        valores.put("nom",bean.nombre)
        valores.put("pat",bean.paterno)
        valores.put("mat",bean.materno)
        valores.put("sexo",bean.sexo)
        valores.put("usuario",bean.usuario)
        valores.put("contrasena",bean.contrasena)
        salida=cn.insert("tb_usuario","cod",valores).toInt()
        return salida
    }
    fun findById(cod: Int): Usuario? {

        lateinit var lista:Usuario
        var usuario: Usuario? = null
        val cn = InitBD().readableDatabase
        val sql = "SELECT * FROM tb_usuario WHERE cod=?"
        val rs = cn.rawQuery(sql, arrayOf(cod.toString()))
        if (rs.moveToNext()) {
            lista = Usuario(
                rs.getInt(0),
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
            )
        }

        return lista
    }








    fun buscarUsuario(usuario: String, contrasena: String): Usuario? {
        var usuarioEncontrado: Usuario? = null // Inicializa como null
        val cn = InitBD().readableDatabase
        val sql = "SELECT * FROM tb_usuario WHERE usuario=? AND contrasena=?"
        val rs = cn.rawQuery(sql, arrayOf(usuario, contrasena))

        // Utiliza try-catch-finally para manejar la base de datos
        try {
            if (rs.moveToNext()) {
                usuarioEncontrado = Usuario(
                    rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                )
            }
        } catch (e: Exception) {
            // Maneja la excepción según sea necesario
            e.printStackTrace()
        } finally {
            // Cierra el cursor y la base de datos
            rs.close()
            cn.close()
        }

        return usuarioEncontrado // Retorna null si no se encontró
    }

    fun update(bean:Usuario):Int{
        var salida=-1
        var cn=InitBD().writableDatabase
        var valores=ContentValues()
        valores.put("nom",bean.nombre)
        valores.put("pat",bean.paterno)
        valores.put("mat",bean.materno)
        valores.put("sexo",bean.sexo)
        valores.put("usuario",bean.usuario)
        valores.put("contrasena",bean.contrasena)
        salida=cn.update("tb_usuario",valores,"cod=?", arrayOf(bean.codigo.toString()))
        return salida
    }
    fun delete(cod:Int):Int{
        var salida=-1
        var cn=InitBD().writableDatabase
        salida=cn.delete("tb_usuario","cod=?", arrayOf(cod.toString()))
        return salida
    }

}