package com.bignerdranch.android.weatherapp.domain.models

class Weather(
    val temperature: String,
    val wind: String,
    val description: String,
    val forecast: Array<DetailedWeather>
)

