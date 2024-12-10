package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.models.city.City

interface CityRepository {
    suspend fun getCities(input: String): List<City>
}