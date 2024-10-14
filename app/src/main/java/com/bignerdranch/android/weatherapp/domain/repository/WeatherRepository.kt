package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.models.weather.Weather

interface WeatherRepository {
     suspend fun getWeatherInfo(city: String) : Weather?
}