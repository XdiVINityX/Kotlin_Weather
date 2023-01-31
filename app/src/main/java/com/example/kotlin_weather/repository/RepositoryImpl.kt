package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.domain.getRussianCities
import com.example.kotlin_weather.domain.getWorldCities

class RepositoryImpl:Repository {
    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getLocalWeatherOfWorldCities(): List<Weather> {
       return getWorldCities()

    }

    override fun getLocalWeatherOfRussianCities(): List<Weather> {
        return getRussianCities()
    }

}