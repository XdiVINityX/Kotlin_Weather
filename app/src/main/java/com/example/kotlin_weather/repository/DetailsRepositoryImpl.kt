package com.example.kotlin_weather.repository

import retrofit2.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource):DetailsRepository {

    override fun getWeatherDetailsFromServer(lat:Double,lon:Double,callback:retrofit2.Callback<WeatherDTO>) {
        remoteDataSource.getWeatherDetails(lat,lon,callback)
    }



}