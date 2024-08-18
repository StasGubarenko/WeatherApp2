package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.models.Weather
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeatherInfo(city: String) : Flow<Response<Weather>>
}