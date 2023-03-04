package com.example.kotlin_weather.repository

import com.example.kotlin_weather.BuildConfig
import com.example.kotlin_weather.utils.YANDEX_API_KEY_NAME
import com.example.kotlin_weather.utils.YANDEX_API_URL
import com.example.kotlin_weather.utils.YANDEX_API_URL_END_POINT
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request


class RemoteDataSource {
    fun getWeatherDetails(requestLink:String,callback: Callback){
        val client = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
        builder.header(YANDEX_API_KEY_NAME, BuildConfig.WEATHER_API_KEY)
        builder.url(requestLink)

        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(callback)//асинхронно
    }
}
