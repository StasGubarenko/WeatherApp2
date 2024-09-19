package com.bignerdranch.android.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase


class MainActivityViewModel(
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val validationFieldUseCase: ValidationFieldUseCase
) : ViewModel() {
     suspend fun loadWeather(city : String) : Weather?{
      return  loadWeatherUseCase.execute(city = city)
    }

    fun validate(input: String): Boolean{
        return validationFieldUseCase.validate(input)
    }
}

