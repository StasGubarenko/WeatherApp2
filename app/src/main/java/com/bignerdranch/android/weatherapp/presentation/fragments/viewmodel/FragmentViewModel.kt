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

    private val _isSuccessResponse = MutableStateFlow<StateWeather>(StateWeather.Loading)

    val isSuccessResponse: StateFlow<StateWeather> = _isSuccessResponse

    private val _currentCities = MutableStateFlow<StateCity>(StateCity.Loading)
    val currentCity: StateFlow<StateCity> = _currentCities

    suspend fun sendWeather(weather: Weather) {
        _resultWeather.emit(weather)
    }

    suspend fun loadCities(input: String): List<City> {
      _currentCities.value = StateCity.Loading

        var cities: List<City> = listOf()

        try {
            cities = loadCityUseCase.loadCities(input)

        }catch (e : Exception){
            _currentCities.value = StateCity.Error
        }

        return cities
    }

    suspend fun loadWeather(city: String) {

        _isSuccessResponse.value = StateWeather.Loading

        try {
            val weather = loadWeatherUseCase.execute(city = city)

            if (weather != null) {
                sendWeather(weather = weather)
                _isSuccessResponse.value = StateWeather.Success
            } else {
                _isSuccessResponse.value = StateWeather.Error
            }
        } catch (e: Exception) {
            _isSuccessResponse.value = StateWeather.Error
        }
    }

    fun validate(input: String): Boolean {
        return validationFieldUseCase.validate(input)
    }

}