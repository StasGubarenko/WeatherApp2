package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase
import com.bignerdranch.android.weatherapp.presentation.fragments.State
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

    private val _isSuccessResponse = MutableStateFlow<State>(State.Loading)

    val isSuccessResponse: StateFlow<State> = _isSuccessResponse

    suspend fun sendWeather(weather: Weather) {
        _resultWeather.emit(weather)
    }

    suspend fun loadCities(input: String): List<City> {
        return loadCityUseCase.loadCities(input = input)
    }

    suspend fun loadWeather(city: String){

        _isSuccessResponse.value = State.Loading

        try {
           val weather= loadWeatherUseCase.execute(city = city)

            if (weather != null){
                sendWeather(weather = weather)
                _isSuccessResponse.value = State.Success
            }else{
                _isSuccessResponse.value = State.Error
            }
        }catch (e : Exception){
            _isSuccessResponse.value = State.Error
        }
    }

    fun validate(input: String): Boolean {
        return validationFieldUseCase.validate(input)
    }
}