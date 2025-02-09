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

    //Согласен, название не соответствует шаблону.
    @Test
    fun getCities_сheckInputParametersInMethod() = runBlocking {

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
            anyString(),
            anyString(),
            anyString()
        )).thenReturn(expectedResponse)


        //capture
        val apiCaptor = ArgumentCaptor.forClass(String::class.java)
        val cityCaptor = ArgumentCaptor.forClass(String::class.java)
        val countCaptor = ArgumentCaptor.forClass(String::class.java)

        mockCityAPI.getCities(
            api = BaseAuth.API_CITY,
            city = INPUT_CITY,
            count = COUNT
        )

        verify(mockCityAPI).getCities(
            api = apiCaptor.capture(),
            city = cityCaptor.capture(),
            count = countCaptor.capture())

        assertEquals(BaseAuth.API_CITY, apiCaptor.value)
        assertEquals(INPUT_CITY, cityCaptor.value)
        assertEquals(COUNT, countCaptor.value)
    }

    @Test
    fun test() = runBlocking{

        val mockCityAPI : CityAPI = mock()

        val cityRepository = CityRepositoryImpl(mockCityAPI)
        val expectedResponse = Response.success(listOf<City>())
        `when`(mockCityAPI.getCities(
            api = BaseAuth.API_CITY,
            city = INPUT_CITY,
            count = COUNT
        ))
            .thenReturn(expectedResponse)

        cityRepository.getCities(inputNameOfCity = INPUT_CITY)

        val argumentApi = ArgumentCaptor.forClass(String::class.java)
        val argumentCity = ArgumentCaptor.forClass(String::class.java)
        val argumentCount = ArgumentCaptor.forClass(String::class.java)

        verify(mockCityAPI).getCities(
            api = argumentApi.capture(),
            city = argumentCity.capture(),
            count = argumentCount.capture()
        )

        assertEquals(INPUT_CITY, argumentCity.value)
    }

    private companion object {
        private const val INPUT_CITY = "Moscow"
        private const val EMPTY_INPUT = ""
        private const val COUNT = "5"
    }
}