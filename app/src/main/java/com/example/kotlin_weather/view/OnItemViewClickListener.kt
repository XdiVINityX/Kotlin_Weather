package com.example.kotlin_weather.view

import com.example.kotlin_weather.domain.Weather

interface OnItemViewClickListener {
    fun onItemClickNewInstanceDetail(weather: Weather)
}