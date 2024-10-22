package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FragmentViewModel(
    private val loadCityUseCase: LoadCityUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val validationFieldUseCase: ValidationFieldUseCase
) : ViewModel() {

    private val _resultWeather = MutableSharedFlow<Weather>(replay = 1)
    val resultWeather: SharedFlow<Weather> get() = _resultWeather


    suspend fun sendWeather(weather: Weather) {
        _resultWeather.emit(weather)
    }

    suspend fun loadCities(input: String): MutableList<City> {
        return loadCityUseCase.loadCities(input = input)
    }

    suspend fun loadWeather(city: String): Weather? {

        val result = loadWeatherUseCase.execute(city = city)

        return result
    }

    fun validate(input: String): Boolean {
        return validationFieldUseCase.validate(input)
    }
}