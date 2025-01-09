package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.models.weather.Condition
import com.bignerdranch.android.weatherapp.domain.models.weather.Current
import com.bignerdranch.android.weatherapp.domain.models.weather.Location
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class LoadWeatherUseCaseTest{

    @Test
    fun test_return_weather(){
        runBlocking {
            val mockWeatherRepository : WeatherRepository = mock()
            val loadWeatherUseCase = LoadWeatherUseCase(weatherRepository = mockWeatherRepository)

            val expectedWeather = Weather(
                location = Location(name = "Omsk", region = "Omsk"),
                current = Current(
                    condition = Condition(text = "", icon = ""),
                    temp_c = 7.3
                )
            )

            `when`(mockWeatherRepository.getWeatherInfo("Omsk")).thenReturn(expectedWeather)
            val actual = loadWeatherUseCase.execute("Omsk")

            assertEquals(expectedWeather, actual)
        }
    }

    @Test
    fun test_call_execute(){
        runBlocking {
            val mockWeatherRepository : WeatherRepository = mock()
            val loadWeatherUseCase = LoadWeatherUseCase(weatherRepository = mockWeatherRepository)

            loadWeatherUseCase.execute("Omsk")

            verify(mockWeatherRepository).getWeatherInfo("Omsk")

        }
    }
}