package com.example.kotlin_weather.repository

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class WeatherLoader( val listener: WeatherLoaderListener, val lat:Double,val lon:Double) {
    fun loadWeather() {

        val url = URL("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}")

        Thread {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty("X-Yandex-API-Key","b8f6d2a0-9894-450d-bebd-ced3101409f7")//TODO разобраться вынос в файл
            urlConnection.connectTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val handler = Handler(Looper.getMainLooper())
            val weatherDTO = Gson().fromJson(reader,WeatherDTO::class.java)
            handler.post{listener.onLoaded(weatherDTO)}
            urlConnection.disconnect()
        }.start()
    }

}