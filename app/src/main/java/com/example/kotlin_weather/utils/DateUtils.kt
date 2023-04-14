package com.example.kotlin_weather.utils

import com.example.kotlin_weather.domain.City
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.domain.getDefaultCity
import com.example.kotlin_weather.repository.WeatherDTO
import com.example.kotlin_weather.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO):Weather{
    return (Weather(getDefaultCity(),weatherDTO.fact.temp,weatherDTO.fact.feels_like,weatherDTO.fact.condition))
}
fun convertHistoryEntityToWeather(entityList:List<HistoryEntity>):List<Weather>{
    return entityList.map{
        Weather(City(it.name,0.0,0.0),it.temperature,0,it.condition)
    }
}
fun convertWeatherToHistoryEntity(weather: Weather):HistoryEntity{
    return HistoryEntity(0,weather.city.name,weather.temperatyre,weather.condiotion)
}
