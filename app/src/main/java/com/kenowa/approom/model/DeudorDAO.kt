package com.kenowa.approom.model

import androidx.room.*

@Dao
interface DeudorDAO {

    @Insert
    fun crearDeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor WHERE nombre LIKE '%' || :nombre || '%'")
    fun buscarDeudor(nombre: String) : Array<Deudor>

    @Query("SELECT * FROM tabla_deudor WHERE nombre LIKE :nombre")
    fun buscarDeudorEspecifico(nombre: String) : Deudor

    @Update
    fun actualizarDeudor(deudor: Deudor)

    @Delete
    fun borrarDeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor")
    fun getDeudores(): List<Deudor>
}