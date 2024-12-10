package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase
import com.bignerdranch.android.weatherapp.presentation.fragments.StateCity
import com.bignerdranch.android.weatherapp.presentation.fragments.StateWeather
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class FragmentViewModel(
    private val loadCityUseCase: LoadCityUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val validationFieldUseCase: ValidationFieldUseCase
) : ViewModel() {

    private val _resultWeather = MutableSharedFlow<Weather>(replay = 1)
    val resultWeather: SharedFlow<Weather> get() = _resultWeather

    private val _isSuccessResponseWeather = MutableStateFlow<StateWeather>(StateWeather.Loading)
    val isSuccessResponseWeather: StateFlow<StateWeather> = _isSuccessResponseWeather

    private val _isSuccessResponseCity = MutableStateFlow<StateCity>(StateCity.Loading)
    val isSuccessResponseCity: StateFlow<StateCity> = _isSuccessResponseCity

    private val _listCity = MutableStateFlow<List<City>>(listOf())
    val listCity = _listCity

    private suspend fun sendWeather(weather: Weather) {
        _resultWeather.emit(weather)
    }

    suspend fun loadCities(input: String){

        _isSuccessResponseCity.value = StateCity.Loading

        try {
         val cities = loadCityUseCase.loadCities(input)

            if (cities.isNotEmpty()){
                listCity.value = cities
                _isSuccessResponseCity.value = StateCity.Success
            }else{
                _isSuccessResponseCity.value = StateCity.Error
            }
        }catch (e : Exception){
            _isSuccessResponseCity.value = StateCity.Error
        }
    }

    suspend fun loadWeather(city: String) {

        _isSuccessResponseWeather.value = StateWeather.Loading

        try {
            val weather = loadWeatherUseCase.execute(city = city)

            if (weather != null) {
                sendWeather(weather = weather)
                _isSuccessResponseWeather.value = StateWeather.Success
            } else {
                _isSuccessResponseWeather.value = StateWeather.Error
            }
        } catch (e: Exception) {
            _isSuccessResponseWeather.value = StateWeather.Error
        }
    }

    fun validate(input: String): Boolean {
        return validationFieldUseCase.validate(input)
    }

}