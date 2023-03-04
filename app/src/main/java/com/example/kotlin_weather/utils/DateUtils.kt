package com.example.kotlin_weather.utils

import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.domain.getDefaultCity
import com.example.kotlin_weather.repository.WeatherDTO

fun convertDtoToModel(weatherDTO: WeatherDTO):List<Weather>{
    return listOf(Weather(getDefaultCity(),weatherDTO.fact.temp,weatherDTO.fact.feels_like,weatherDTO.fact.condition))
}
