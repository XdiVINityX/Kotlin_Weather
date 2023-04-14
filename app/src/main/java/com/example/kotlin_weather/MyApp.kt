package com.example.kotlin_weather

import android.app.Application
import androidx.room.Room
import com.example.kotlin_weather.room.HistoryDAO
import com.example.kotlin_weather.room.HistoryDataBase

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstanse = this
}

    companion object {
        private var appInstanse: MyApp? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "HistoryDataBase.db"

        fun getHistoryDAO(): HistoryDAO {
            if (db == null) {
                if (appInstanse != null) {
                    db = Room.databaseBuilder(appInstanse!!, HistoryDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                } else {
                    throw IllegalStateException("appInstance == null)")
                }
            }
            return db!!.historyDAO()
        }
    }
}