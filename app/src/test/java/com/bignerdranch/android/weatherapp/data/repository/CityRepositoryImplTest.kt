package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.CityAPI
import com.bignerdranch.android.weatherapp.data.util.BaseAuth
import com.bignerdranch.android.weatherapp.domain.models.city.City
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response

class CityRepositoryImplTest{

    @Test
    fun getCities_WhenResponseIsSuccessAndNotEmpty_ReturnListOfCities() = runBlocking{
        //setup
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
        `when`(mockCityAPI.getCities(
            api = anyString(),
            city = anyString(),
            count = anyString()))
            .thenReturn(expectedResponse)

        //act
        val actual = сityRepositoryImpl.getCities(inputNameOfCity = INPUT_CITY)

        //assert
        assertEquals(expectedResponse.body(), actual)
    }

    @Test
    fun getCities_WhenResponseIsSuccessAndBodyIsEmpty_ReturnEmptyListOfCities() = runBlocking {
        //setup
        val mockCityAPI : CityAPI = mock()
        val cityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)
        val expectedResponse = Response.success(listOf<City>())
        `when`(mockCityAPI.getCities(
            api = anyString(),
            city = anyString(),
            count = anyString()))
            .thenReturn(expectedResponse)

        //act
        val actual = cityRepositoryImpl.getCities(inputNameOfCity = INPUT_CITY)

        //assert
        assertEquals(expectedResponse.body(), actual)
    }

    @Test
    fun getCities_WhenResponseIsNotSuccess_ReturnEmptyListOfCities() = runBlocking {
        //setup
        val mockCityAPI: CityAPI = mock()
        val mockCityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)
        val expected = listOf<List<City>>()
        val mockExpected = Response.success(listOf<City>())
        `when`(mockCityAPI.getCities(
            api = anyString(),
            city = anyString(),
            count = anyString()))
            .thenReturn(mockExpected)

        //act
        val actual = mockCityRepositoryImpl.getCities(inputNameOfCity = EMPTY_INPUT)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun getCities_сheckInputParametersInMethod(): Unit = runBlocking {

        val mockCityAPI : CityAPI = mock()

        val expectedResponse = Response.success(listOf(City(
            name = "Moscow",
            latitude = 44.3,
            longitude = 44.3,
            country = "Russia",
            population = 33L ,
            isCapital = false
        )))

        `when`(mockCityAPI.getCities(
            api = BaseAuth.API_CITY,
            city = INPUT_CITY,
            count = COUNT
        )).thenReturn(expectedResponse)

        mockCityAPI.getCities(
            api = BaseAuth.API_CITY,
            city = INPUT_CITY,
            count = COUNT
        )


        verify(mockCityAPI).getCities(
            api = BaseAuth.API_CITY,
            city = INPUT_CITY,
            count = COUNT
        )
    }

    private companion object {
        private const val INPUT_CITY = "Moscow"
        private const val EMPTY_INPUT = ""
        private const val COUNT = "5"
    }
}