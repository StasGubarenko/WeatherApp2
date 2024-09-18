package com.bignerdranch.android.weatherapp.data.retrofit

import com.bignerdranch.android.weatherapp.domain.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {

    @GET("/v1/current.json")
     suspend fun getInfo(@Query("key") api: String, @Query("q") city: String): Response<Weather>
}
