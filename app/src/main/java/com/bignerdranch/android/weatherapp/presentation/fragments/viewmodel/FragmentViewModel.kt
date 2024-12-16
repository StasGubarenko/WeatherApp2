package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase
import com.bignerdranch.android.weatherapp.presentation.fragments.News
import com.bignerdranch.android.weatherapp.presentation.fragments.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FragmentViewModel(
    private val loadCityUseCase: LoadCityUseCase,
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val validationFieldUseCase: ValidationFieldUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        State.Content(
            cities = listOf(),
            searchHint = null,
            searchColor = null,
            weather = null,
            isValidateInputText = false

    ))
    val state: StateFlow<State> = _state

    private val _news: MutableSharedFlow<News> = MutableSharedFlow()
    val news: SharedFlow<News> = _news

     fun loadCities(input: String) {
         val currentState = _state.value
         if (validationFieldUseCase.validate(input)){
             viewModelScope.launch {
                 try {
                     if (currentState is State.Content) {
                         val cities = loadCityUseCase.loadCities(input)
                         val citiesNames = cities.map { it.name }
                         _state.value = currentState.copy(
                             cities = citiesNames,
                             isValidateInputText = true
                         )
                     }
                 } catch (e: Exception) {
                     _news.emit(News.ShowError)
                 }
             }
         }else{
             if (input.isNotEmpty()){
                 _state.value =  (currentState as State.Content).copy(
                     searchHint = R.string.error_message,
                     searchColor = Color.RED,
                     isValidateInputText = false
                 )
             }else{
                 _state.value =  (currentState as State.Content).copy(
                     searchHint = null,
                     searchColor = null,
                     isValidateInputText = false
                 )
             }
         }
    }

     fun loadWeather(city: String) {
        var currentState = _state.value
        if (currentState is State.Content && validationFieldUseCase.validate(city)) {

            _state.value = State.Loading(
                isVisible = true,
                buttonText = R.string.button_loader
            )

            viewModelScope.launch {
                try {
                    val weather = loadWeatherUseCase.execute(city = city)
                    Log.d("TESTIM","after variable weather ${currentState.weather?.location?.name}")
                    if (weather != null) {
                        _state.value = currentState.copy(weather = weather)
                        Log.d("TESTIM","${currentState.weather?.location?.name}")
                        _news.emit(News.NavigateForward)
                    } else {
                        _news.emit(News.ShowError)
                    }
                } catch (e: Exception) {
                    _news.emit(News.ShowError)
                }

                /**
                 * Здесь удалил
                 * finally{
                 * _state.value = State.Content
                 * }
                 *Удалил, так как данная строчка сбрасывала значение weather, поэтому на фрагменте
                 * DetailedFragment не отображались данные по погоде.
                 * Подскажи пж можно было так сделать или для weather лучше создать отдельную переменную, которая хранила бы
                 * значение weather?
                 */
            }
        }
    }
}