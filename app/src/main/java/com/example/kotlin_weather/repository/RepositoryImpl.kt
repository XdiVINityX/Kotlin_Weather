package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.domain.getRussianCities
import com.example.kotlin_weather.domain.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromRemoteSource() = Weather()

    override fun getWeatherFromLocalSource() = Weather()

    override fun getLocalWeatherOfWorldCities() = getWorldCities()

    override fun getLocalWeatherOfRussianCities() = getRussianCities()
}