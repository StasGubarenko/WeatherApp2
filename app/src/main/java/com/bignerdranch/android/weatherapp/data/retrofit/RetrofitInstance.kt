package com.bignerdranch.android.weatherapp.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val cityAPI: CityAPI by lazy {
        createRetrofit(URL_CITY).create(CityAPI::class.java)
    }

    val weatherAPI: WeatherAPI by lazy {
        createRetrofit(URL_WEATHER).create(WeatherAPI::class.java)
    }

    private const val URL_WEATHER = "https://api.weatherapi.com/v1/current.json/"
    private const val URL_CITY = "https://api.api-ninjas.com/v1/city/"

    private fun createRetrofit(url: String) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}