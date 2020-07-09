package com.kenowa.approom.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Registro::class), version = 1)
abstract class RegistroDataBase : RoomDatabase() {

    abstract fun RegistroDAO(): RegistroDAO
}