package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.retrofit.WeatherAPI
import com.bignerdranch.android.weatherapp.domain.models.weather.Condition
import com.bignerdranch.android.weatherapp.domain.models.weather.Current
import com.bignerdranch.android.weatherapp.domain.models.weather.Location
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class WeatherRepositoryImplTest{

    @Test
    fun test_return_body_when_response_is_successful() = runBlocking {
        val mockWeatherAPI: WeatherAPI = mock()
        val weatherRepositoryImpl = WeatherRepositoryImpl(weatherAPI = mockWeatherAPI)
        val mockResponse = mock(Response::class.java) as Response<Weather>
        val expectedWeather = Weather(
            location = Location(name = "Omsk", region = "Omsk"),
            current = Current(
                condition = Condition(text = "", icon = ""),
                temp_c = 7.3
            )
        )

        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(expectedWeather)
        `when`(mockWeatherAPI.getInfo(api = anyString(), city = anyString())).thenReturn(mockResponse)

        val actual = weatherRepositoryImpl.getWeatherInfo("Omsk")
        assertEquals(expectedWeather, actual)
    }

    @Test
    fun test_return_null_when_response_is_not_successful() = runBlocking {
        val mockWeatherAPI: WeatherAPI = mock()
        val weatherRepositoryImpl = WeatherRepositoryImpl(weatherAPI = mockWeatherAPI)
        val mockResponse: Response<Weather> = mock(Response::class.java) as Response<Weather>
        val expected = null

        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockWeatherAPI.getInfo(anyString(), anyString())).thenReturn(mockResponse)

        val actual = weatherRepositoryImpl.getWeatherInfo("")
        assertEquals(expected, actual)
    }
}