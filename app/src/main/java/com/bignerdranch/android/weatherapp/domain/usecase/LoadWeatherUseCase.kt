package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.data.repository.WeatherRepositoryImp
import com.bignerdranch.android.weatherapp.domain.models.Weather

class LoadWeatherUseCase(private val weatherRepository: WeatherRepositoryImp) {

    suspend fun execute(city : String) : Weather{
        weatherRepository.getWeatherInfo(city = city)
    }
}