package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather

interface LocalRepository {
    fun getAllHistory():List<Weather>
    fun saveEntity(weather: Weather)

}