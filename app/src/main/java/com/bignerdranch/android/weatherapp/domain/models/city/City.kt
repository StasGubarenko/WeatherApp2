package com.bignerdranch.android.weatherapp.domain.models.city

import com.google.gson.annotations.SerializedName

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val population: Long,
    @SerializedName("is_capital")
    val isCapital: Boolean
)

