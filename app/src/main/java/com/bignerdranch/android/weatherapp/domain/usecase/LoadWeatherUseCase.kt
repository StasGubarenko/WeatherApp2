package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.models.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository

//Почему передали в LoadWeatherUseCase?
//Почему тип данных WeatherRepositoryImp?
//Что должен вернуть execute?
class LoadWeatherUseCase(private val weatherRepository: WeatherRepository) {

     suspend fun execute(city : String) : Weather?{
       return weatherRepository.getWeatherInfo(city = city)
    }
}