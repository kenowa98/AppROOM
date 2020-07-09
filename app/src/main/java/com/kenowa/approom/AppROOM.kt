package com.kenowa.approom

import android.app.Application
import androidx.room.Room
import com.kenowa.approom.model.DeudorDataBase
import com.kenowa.approom.model.RegistroDataBase

class AppROOM : Application() {

    companion object{
        lateinit var database: DeudorDataBase
        lateinit var database2: RegistroDataBase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudoresDB"
        ).allowMainThreadQueries().build()

        database2 = Room.databaseBuilder(
            this,
            RegistroDataBase::class.java,
            "registrosDB"
        ).allowMainThreadQueries()
            .build()
    }
}