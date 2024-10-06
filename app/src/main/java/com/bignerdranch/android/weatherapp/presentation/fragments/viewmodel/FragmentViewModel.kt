package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
//import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase

class FragmentViewModel(
    private val loadCityUseCase: LoadCityUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase
) : ViewModel() {

    val weathers = MutableLiveData<Weather>()

    suspend fun loadCities(input: String) : MutableList<City>{
        return loadCityUseCase.loadCities(input = input)
    }

    suspend fun loadWeather(city : String) : Weather?{
        return  loadWeatherUseCase.execute(city = city)
    }

    fun sendCity(weather: Weather){
        weathers.value = weather
    }
}