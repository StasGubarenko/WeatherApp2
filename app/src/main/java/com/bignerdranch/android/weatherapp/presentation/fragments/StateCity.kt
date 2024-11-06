package com.bignerdranch.android.weatherapp.presentation.fragments

sealed interface StateCity {
    data object Loading: StateCity
    data object Error: StateCity
}