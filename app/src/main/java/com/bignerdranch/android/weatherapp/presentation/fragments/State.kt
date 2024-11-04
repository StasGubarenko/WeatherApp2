package com.bignerdranch.android.weatherapp.presentation.fragments

import com.bignerdranch.android.weatherapp.domain.models.weather.Current
import com.bignerdranch.android.weatherapp.domain.models.weather.Location
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather

sealed interface State{
    data object Error : State
    data object Success : State

    data object Loading: State
}
