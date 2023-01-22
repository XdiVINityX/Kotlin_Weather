package com.example.kotlin_weather.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperatyre: Int = -1,
    val feelsLike: Int = -5
)

fun getDefaultCity(): City {
    return City("Moscow", 55.0, 37.0)
}