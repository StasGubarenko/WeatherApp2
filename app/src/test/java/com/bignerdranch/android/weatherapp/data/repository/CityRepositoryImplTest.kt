package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.CityAPI
import com.bignerdranch.android.weatherapp.domain.models.city.City
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class CityRepositoryImplTest{

    @Test
    fun test_return_body_when_response_is_successful_and_body_is_not_empty() = runBlocking{
        val mockCityAPI : CityAPI = mock()
        val сityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)

        val expectedResponse = Response.success(listOf(City(
            name = "Omsk",
            latitude = 44.3,
            longitude = 44.3,
            country = "Russia",
            population = 33L ,
            isCapital = false
        )))

        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(expectedResponse)

        val actual = сityRepositoryImpl.getCities("Omsk")

        assertEquals(expectedResponse.body(), actual)
    }

    @Test
    fun test_return_list_when_response_is_successful_and_body_is_empty() = runBlocking {
        val mockCityAPI : CityAPI = mock()

        val cityRepositoryImpl = CityRepositoryImpl(mockCityAPI)

        val expectedResponse = Response.success(listOf<City>())

        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(expectedResponse)

        val actual = cityRepositoryImpl.getCities("Omsk")

        assertEquals(expectedResponse.body(), actual)
    }

    @Test
    fun test_return_list_when_response_is_not_successful() = runBlocking {
        val mockCityAPI: CityAPI = mock()
        val mockCityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)

        val expected = listOf<List<City>>()

        val test = Response.success(listOf<City>())

        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(test)

        val actual = mockCityRepositoryImpl.getCities("")

        assertEquals(expected,actual)
    }
}