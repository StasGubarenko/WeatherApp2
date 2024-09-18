package com.bignerdranch.android.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase


class MainActivityViewModel(
    private val loadWeatherUseCase: LoadWeatherUseCase
) : ViewModel() {
     suspend fun loadWeather(city : String) : Weather?{
      return  loadWeatherUseCase.execute(city = city)
    }
}

