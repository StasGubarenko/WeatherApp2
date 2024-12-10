package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance
import com.bignerdranch.android.weatherapp.data.util.BaseAuth
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.repository.CityRepository

class CityRepositoryImpl: CityRepository {

    override suspend fun getCities(input: String): List<City> {
       val response = RetrofitInstance.cityAPI.getCities(
           api = BaseAuth.API_CITY,
           city = input,
           count = COUNT
       )

       return if (response.isSuccessful){
            response.body() ?: listOf()
       }else{
            listOf()
        }
    }

    companion object{
        private const val COUNT = "5"
    }
}