package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.repository.CityRepository

class LoadCityUseCase(
    private val cityRepository: CityRepository
) {
    suspend fun loadCities(input: String) : List<City>{
        return cityRepository.getCities(input)
    }
}