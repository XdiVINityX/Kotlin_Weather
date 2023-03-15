package com.example.kotlin_weather.utils

import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.domain.getDefaultCity
import com.example.kotlin_weather.repository.WeatherDTO

fun convertDtoToModel(weatherDTO: WeatherDTO):Weather{
    return (Weather(getDefaultCity(),weatherDTO.fact.temp,weatherDTO.fact.feels_like,weatherDTO.fact.condition))
}
