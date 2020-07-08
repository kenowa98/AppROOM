package com.kenowa.approom

import android.app.Application
import androidx.room.Room
import com.kenowa.approom.model.DeudorDataBase

class AppROOM : Application() {

    companion object{
        lateinit var database: DeudorDataBase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudoresDB"
        ).allowMainThreadQueries().build()
    }
}