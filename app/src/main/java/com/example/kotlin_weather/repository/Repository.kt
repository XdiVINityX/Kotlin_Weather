package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather

interface Repository {
    fun getWeatherFromRemoteSource():Weather
    fun getWeatherFromLocalSource():Weather
}