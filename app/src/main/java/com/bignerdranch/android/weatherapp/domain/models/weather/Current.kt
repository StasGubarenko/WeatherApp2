package com.bignerdranch.android.weatherapp.domain.models.weather

data class Current(
    val condition: Condition,
    val temp_c: Double
)
