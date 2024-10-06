package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.RetrofitInstance
import com.bignerdranch.android.weatherapp.data.util.BaseAuth
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.repository.CityRepository

class CityRepositoryImp: CityRepository {

    override suspend fun getCities(input: String): MutableList<City> {
       val response = RetrofitInstance.cityAPI.getCities(
           api = BaseAuth.API_CITY,
           city = input,
           count = "5"
       )

       return if (response.isSuccessful){
            response.body() ?: mutableListOf()
       }else{
            mutableListOf()
        }
    }
}