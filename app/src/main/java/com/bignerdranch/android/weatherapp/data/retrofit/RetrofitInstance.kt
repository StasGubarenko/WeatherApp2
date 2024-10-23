package com.bignerdranch.android.weatherapp.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val URL_WEATHER = "https://api.weatherapi.com/v1/current.json/"
    private const val URL_CITY = "https://api.api-ninjas.com/v1/city/"


    val weatherAPI = createRetrofit(URL_WEATHER).create(WeatherAPI::class.java)
    val cityAPI = createRetrofit(URL_CITY).create(CityAPI::class.java)

    private fun createRetrofit(url: String) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}