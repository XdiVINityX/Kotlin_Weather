package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.room.HistoryDAO
import com.example.kotlin_weather.utils.convertHistoryEntityToWeather
import com.example.kotlin_weather.utils.convertWeatherToHistoryEntity

class LocalRepositoryIml(private val localDataSource:HistoryDAO):LocalRepository {

    override fun getAllHistory(): List<Weather> {
       return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToHistoryEntity(weather))
    }
}