package com.kenowa.approom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegistroDAO {

    @Insert
    fun crearRegistro(registro: Registro)

    @Query("SELECT * FROM tabla_registro WHERE correo LIKE :correo AND clave LIKE :clave")
    fun buscarRegistro(correo: String, clave: String): Registro
}