package com.bignerdranch.android.weatherapp.data.retrofit

import com.bignerdranch.android.weatherapp.domain.models.city.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityAPI {

    @GET("/v1/city")
    suspend fun getCities(
        @Header("X-Api-Key") api: String,
        @Query("name") city: String,
        @Query("limit") count: String
    ): Response<ArrayList<City>>
}