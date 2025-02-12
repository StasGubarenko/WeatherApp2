package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance
import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance.weatherAPI
import com.bignerdranch.android.weatherapp.data.retrofit.WeatherAPI
import com.bignerdranch.android.weatherapp.data.util.BaseAuth
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherAPI: WeatherAPI
) : WeatherRepository {

    override suspend fun getWeatherInfo(city: String): Weather? {

        val response = weatherAPI.getInfo(api = BaseAuth.API_WEATHER, city = city)

        return if (response.isSuccessful) {

            response.body()

        } else {

            null

        }

    }
}