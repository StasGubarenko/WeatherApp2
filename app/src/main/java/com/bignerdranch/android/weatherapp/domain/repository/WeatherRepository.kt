package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.models.weather.Weather

//Почему WeatherRepository находится в слое domain?
// Что такое репозиторий?
interface WeatherRepository {
     suspend fun getWeatherInfo(city: String) : Weather?
}