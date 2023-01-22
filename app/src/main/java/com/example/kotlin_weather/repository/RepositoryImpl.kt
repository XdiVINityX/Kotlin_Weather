package com.example.kotlin_weather.repository

import com.example.kotlin_weather.domain.Weather

class RepositoryImpl:Repository {
    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

}