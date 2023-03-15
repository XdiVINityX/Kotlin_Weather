package com.example.kotlin_weather.viewmodel

import com.example.kotlin_weather.domain.Weather

sealed class AppState{
    object Loading: AppState()
    data class Success(val weatherData:List<Weather>):AppState()
    data class SuccessForDetails(val weatherData:Weather):AppState()
    data class Error(val error:Throwable):AppState()
    //data class Error(val error:String):AppState()
}
