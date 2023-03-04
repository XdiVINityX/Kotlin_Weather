package com.example.kotlin_weather.view.details

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_weather.BuildConfig
import com.example.kotlin_weather.repository.WeatherDTO
import com.example.kotlin_weather.utils.YANDEX_API_KEY_NAME
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


const val LATITUDE_EXTRA = "latitude"
const val LONGITUDE_EXTRA = "longitude"

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"

class DetailsService(name: String = "details") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val lat = it.getDoubleExtra(LATITUDE_EXTRA,-1.0)
            val lon = it.getDoubleExtra(LONGITUDE_EXTRA,-2.0)
            loadWeather(lat,lon)
        }

    }

    fun loadWeather(lat:Double,lon:Double) {
        val url = URL("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}")
        Thread {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(
                YANDEX_API_KEY_NAME,
                BuildConfig.WEATHER_API_KEY
            )
            urlConnection.connectTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val handler = Handler(Looper.getMainLooper())
            val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
            //handler.post { listener.onLoaded(weatherDTO) }

            val mySendIntent = Intent(DETAILS_INTENT_FILTER)
            mySendIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA,weatherDTO)
            LocalBroadcastManager.getInstance(this).sendBroadcast(mySendIntent)




            urlConnection.disconnect()
        }.start()
    }
}