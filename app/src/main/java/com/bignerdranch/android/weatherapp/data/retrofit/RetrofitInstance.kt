package com.bignerdranch.android.weatherapp.data.retrofit

import com.bignerdranch.android.weatherapp.data.util.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: WeatherAPI by lazy{
        Retrofit.Builder()
        .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}