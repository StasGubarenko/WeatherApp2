package com.bignerdranch.android.weatherapp.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    const val URL = "https://api.weatherapi.com/v1/current.json/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherAPI = retrofit.create(WeatherAPI::class.java)


}