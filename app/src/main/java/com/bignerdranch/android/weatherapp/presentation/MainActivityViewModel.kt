package com.bignerdranch.android.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase

class MainActivityViewModel(
    private val loadWeatherUseCase: LoadWeatherUseCase
) : ViewModel() {
    fun loadWeather(city : String){
        loadWeatherUseCase.execute(city = city)
    }
}