package com.bignerdranch.android.weatherapp.domain.models

data class CurrentConditions(
    val condition: Condition,
    val temp_c: Double
)
