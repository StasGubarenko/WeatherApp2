package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository

class LoadWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun execute(city: String): Weather? {
        return weatherRepository.getWeatherInfo(city = city)
    }
}