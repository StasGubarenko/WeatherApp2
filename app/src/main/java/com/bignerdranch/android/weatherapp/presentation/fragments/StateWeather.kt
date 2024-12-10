package com.bignerdranch.android.weatherapp.presentation.fragments

sealed interface StateWeather{
    data object Error : StateWeather
    data object Success : StateWeather
    data object Loading: StateWeather
}
