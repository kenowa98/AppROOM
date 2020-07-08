package com.kenowa.approom.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Deudor::class], version = 1)
abstract class DeudorDataBase : RoomDatabase() {

    abstract fun DeudorDAO() : DeudorDAO

}