package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.repository.CityRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class LoadCityUseCaseTest{

    @Test
    fun test_return_list(){
        runBlocking {
            val mockCityRepository: CityRepository = mock()
            val loadCityUseCase = LoadCityUseCase(cityRepository = mockCityRepository)
            val expected = listOf<City>()
            `when`(mockCityRepository.getCities("")).thenReturn(expected)
            val actual = loadCityUseCase.loadCities("")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun test_verify_call_loadCities(){
        runBlocking {
            val mockCityRepository: CityRepository = mock()
            val loadCityUseCase = LoadCityUseCase(cityRepository = mockCityRepository)

            loadCityUseCase.loadCities("")
            verify(mockCityRepository).getCities("")
        }
    }
}