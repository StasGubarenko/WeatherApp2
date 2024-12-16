package com.bignerdranch.android.weatherapp.presentation.fragments

import android.graphics.Color
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather

/**
 * @author e.petrashko
 */
sealed interface State {

    data class Content(
        val cities: List<String>,
        val searchColor: Int?,
        val searchHint: Int?,
        val weather: Weather?,
        val isValidateInputText: Boolean
    ) : State

    data class Loading(
        val isVisible: Boolean,
        val buttonText: Int
    ) : State

}
