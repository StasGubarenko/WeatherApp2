package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance
import com.bignerdranch.android.weatherapp.data.util.BaseAuth
import com.bignerdranch.android.weatherapp.domain.models.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImp() : WeatherRepository {

    override suspend fun getWeatherInfo(city: String): Weather? {

            val response = RetrofitInstance.weatherAPI.getInfo(api = BaseAuth.API, city = city)

         return  if (response.isSuccessful){

             response.body()
            }else{
                null
            }

        }
    }