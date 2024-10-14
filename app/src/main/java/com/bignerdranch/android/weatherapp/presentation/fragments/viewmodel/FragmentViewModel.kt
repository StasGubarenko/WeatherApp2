package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.presentation.fragments.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FragmentViewModel(
    private val loadCityUseCase: LoadCityUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase
) : ViewModel() {

    private val resultWeather = MutableSharedFlow<Weather>()
    val _resultWeather get() = resultWeather


     suspend fun sendWeather(weather: Weather){
        resultWeather.emit(weather)
    }
    suspend fun loadCities(input: String) : MutableList<City>{
        return loadCityUseCase.loadCities(input = input)
    }

    suspend fun loadWeather(city: String) : Weather?{

        val result = loadWeatherUseCase.execute(city = city)

        return  result
    }

}