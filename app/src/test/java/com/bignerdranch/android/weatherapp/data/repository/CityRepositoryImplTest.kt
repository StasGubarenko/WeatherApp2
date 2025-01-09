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

        //arrange
        val mockCityAPI : CityAPI = mock()
        val сityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)

        val expectedResponse = listOf(City(
            name = "Omsk",
            latitude = 44.3,
            longitude = 44.3,
            country = "Russia",
            population = 33L ,
            isCapital = false
        ))

        val response: Response<List<City>> = mock(Response::class.java) as Response<List<City>>

        `when`(response.isSuccessful).thenReturn(true)

        `when`(response.body()).thenReturn(expectedResponse)

        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(response)

        val actual = сityRepositoryImpl.getCities("Omsk")

        assertEquals(expectedResponse, actual)
    }

    @Test
    fun test_return_list_when_response_is_successful_and_body_is_empty() = runBlocking {
        val mockCityAPI : CityAPI = mock()
        val mockResponse: Response<List<City>> = mock(Response::class.java) as Response<List<City>>
        val cityRepositoryImpl = CityRepositoryImpl(mockCityAPI)

        val expectedResponse = listOf<City>()

        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(expectedResponse)
        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(mockResponse)

        val actual = cityRepositoryImpl.getCities("Omsk")
        assertEquals(expectedResponse, actual)
    }

    @Test
    fun test_return_list_when_response_is_not_successful() = runBlocking {
        val mockCityAPI: CityAPI = mock()
        val mockCityRepositoryImpl = CityRepositoryImpl(cityAPI = mockCityAPI)
        val mockResponse: Response<List<City>> = mock(Response::class.java) as Response<List<City>>

        val expected = listOf<List<City>>()

        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockCityAPI.getCities(anyString(), anyString(), anyString())).thenReturn(mockResponse)

        val actual = mockCityRepositoryImpl.getCities("")
        assertEquals(expected,actual)
    }
}