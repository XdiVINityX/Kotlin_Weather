package com.example.kotlin_weather.repository

import okhttp3.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(requestLink:String,callback: Callback)
}