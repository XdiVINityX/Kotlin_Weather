package com.example.kotlin_weather.repository



interface DetailsRepository {
    fun getWeatherDetailsFromServer(lat:Double,lon:Double,callback:retrofit2.Callback<WeatherDTO>)
}