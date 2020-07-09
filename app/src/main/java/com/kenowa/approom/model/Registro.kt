package com.kenowa.approom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_registro")
class Registro(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "clave") val clave: String,
    @ColumnInfo(name = "celular") val celular: String,
    @ColumnInfo(name = "genero") val genero: String
)