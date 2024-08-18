package com.bignerdranch.android.weatherapp.data.retrofit

import com.bignerdranch.android.weatherapp.domain.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface WeatherAPI {

    @GET("weather/{city}")
    suspend fun getInfo(@Path("city") city: String): Response<Weather>
}