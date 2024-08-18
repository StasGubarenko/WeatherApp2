package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance
import com.bignerdranch.android.weatherapp.domain.models.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class WeatherRepositoryImp : WeatherRepository {

    override suspend fun getWeatherInfo(city: String): Flow<Response<Weather>> {
        return flow {
            val response = RetrofitInstance.api.getInfo(city)

            if (response.isSuccessful){
                //Какая - то логика
            }else{
                //Обработка ошибки
            }
        }
    }
}