package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import android.graphics.Color
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidateFieldUseCase
import com.bignerdranch.android.weatherapp.presentation.state.State
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Condition
import com.bignerdranch.android.weatherapp.domain.models.weather.Current
import com.bignerdranch.android.weatherapp.domain.models.weather.Location
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.presentation.state.News
import com.bignerdranch.android.weatherapp.rule.MainDispatcherRule
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Rule
import java.lang.RuntimeException
import kotlinx.coroutines.test.advanceUntilIdle


class FragmentViewModelTest{

    @JvmField
    @Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test_change_state_content_when_validate_is_false_and_input_is_isNotEmpty(){
        val loadCityUseCase: LoadCityUseCase = mock()
        val loadWeatherUseCase: LoadWeatherUseCase = mock()
        val validateFieldUseCase: ValidateFieldUseCase = mock()

        val fragmentViewModel = FragmentViewModel(
            loadCityUseCase = loadCityUseCase,
            loadWeatherUseCase = loadWeatherUseCase,
            validateFieldUseCase = validateFieldUseCase
        )

        val input = "+"
        val expectedText = R.string.error_message
        val expectedColor = Color.RED

          `when`(validateFieldUseCase.validate(input)).thenReturn(false)

           fragmentViewModel.loadCities(input)

           val updateState = fragmentViewModel.state.value as State.Content

           assertEquals(updateState.currentText, input)
           assertEquals(expectedText, updateState.searchHint)
           assertEquals(expectedColor, updateState.searchColor)
           assertFalse(updateState.isValidateInputText)
    }

    @Test
    fun test_change_state_content_when_validate_is_false_and_input_is_Empty(){
        val loadCityUseCase: LoadCityUseCase = mock()
        val loadWeatherUseCase: LoadWeatherUseCase = mock()
        val validateFieldUseCase: ValidateFieldUseCase = mock()

        val mockFragmentViewModel = FragmentViewModel(
            loadCityUseCase = loadCityUseCase,
            loadWeatherUseCase = loadWeatherUseCase,
            validateFieldUseCase = validateFieldUseCase
        )

        val input = ""

        `when`(validateFieldUseCase.validate(input)).thenReturn(false)

        mockFragmentViewModel.loadCities(input)

        val updateState = mockFragmentViewModel.state.value as State.Content

        assertEquals(input, updateState.currentText)
        assertNull(updateState.searchHint)
        assertNull(updateState.searchColor)
        assertFalse(updateState.isValidateInputText)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_change_state_content_when_validate_is_true_and_not_exception(){
runTest {
    val loadCityUseCase: LoadCityUseCase = mock()
    val loadWeatherUseCase: LoadWeatherUseCase = mock()
    val validateFieldUseCase: ValidateFieldUseCase = mock()

    val fragmentViewModel = FragmentViewModel(
        loadCityUseCase = loadCityUseCase,
        loadWeatherUseCase = loadWeatherUseCase,
        validateFieldUseCase = validateFieldUseCase
    )

    val input = "Omsk"
    val expectedResponse = listOf(City(
        name = "Omsk",
        latitude = 44.3,
        longitude = 44.3,
        country = "Russia",
        population = 33L ,
        isCapital = false
    ))

    `when`(validateFieldUseCase.validate(input)).thenReturn(true)
    `when`(loadCityUseCase.loadCities(input)).thenReturn(expectedResponse)

    fragmentViewModel.loadCities(input)
          advanceUntilIdle()
    val updateState = fragmentViewModel.state.value as State.Content

    assertTrue(updateState.currentText == input)
    assertNotNull(updateState.cities)
    assertTrue(updateState.isValidateInputText)
}
    }

    @Test
    fun test_change_state_content_when_validate_is_true_and_is_exception(){
        runTest {
            val loadCityUseCase: LoadCityUseCase = mock()
            val loadWeatherUseCase: LoadWeatherUseCase = mock()
            val validateFieldUseCase: ValidateFieldUseCase = mock()

            val fragmentViewModel = FragmentViewModel(
                loadCityUseCase = loadCityUseCase,
                loadWeatherUseCase = loadWeatherUseCase,
                validateFieldUseCase = validateFieldUseCase
            )

            val input = "Omsk"

            `when`(validateFieldUseCase.validate(input)).thenReturn(true)

            `when`(loadCityUseCase.loadCities(input)).thenThrow(RuntimeException())

            fragmentViewModel.loadCities(input)
             val updateState = fragmentViewModel.news.first()

            assertTrue(updateState is News.ShowError)
        }
    }

    @Test
    fun test_state_loading_weather_when_currentState_is_State_Content_and_validate_is_true() {
        val loadCityUseCase: LoadCityUseCase = mock()
        val loadWeatherUseCase: LoadWeatherUseCase = mock()
        val validateFieldUseCase: ValidateFieldUseCase = mock()

        val mockFragmentViewModel = FragmentViewModel(
            loadCityUseCase = loadCityUseCase,
            loadWeatherUseCase = loadWeatherUseCase,
            validateFieldUseCase = validateFieldUseCase
        )

        val input = "Omsk"
        val expectTextOnButton = R.string.button_loader
        `when`(validateFieldUseCase.validate(input)).thenReturn(true)

        mockFragmentViewModel.loadWeather(input)

        val updateState = mockFragmentViewModel.state.value

        if (updateState is State.Loading) {
            assertTrue(updateState.isVisible)
            assertEquals(expectTextOnButton, updateState.buttonText)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_state_loading_weather_when_loadWeatherUseCase_has_exception(){
        runTest {

            val loadCityUseCase: LoadCityUseCase = mock()
            val loadWeatherUseCase: LoadWeatherUseCase = mock()
            val validateFieldUseCase: ValidateFieldUseCase = mock()

            val mockFragmentViewModel = FragmentViewModel(
                loadCityUseCase = loadCityUseCase,
                loadWeatherUseCase = loadWeatherUseCase,
                validateFieldUseCase = validateFieldUseCase
            )

            val city = "Omsk"
            val expectedText = R.string.search_button

            `when`(validateFieldUseCase.validate(city)).thenReturn(true)

            `when`(loadWeatherUseCase.execute(city = city)).thenThrow(RuntimeException())

            mockFragmentViewModel.loadWeather(city)

            val updateStateNews = mockFragmentViewModel.news.first()

            val updateState = mockFragmentViewModel.state.value as State.Loading

            advanceUntilIdle()

            assertTrue(updateStateNews is News.ShowError)
            assertFalse(updateState.isVisible)
            assertEquals(updateState.buttonText, expectedText)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_state_loading_weather_when_weather_is_null(){
        runTest {

            val loadCityUseCase: LoadCityUseCase = mock()
            val loadWeatherUseCase: LoadWeatherUseCase = mock()
            val validateFieldUseCase: ValidateFieldUseCase = mock()

            val mockFragmentViewModel = FragmentViewModel(
                loadCityUseCase = loadCityUseCase,
                loadWeatherUseCase = loadWeatherUseCase,
                validateFieldUseCase = validateFieldUseCase
            )

            val city = "Omsk"
            val expectedText = R.string.search_button

            `when`(validateFieldUseCase.validate(city)).thenReturn(true)

            `when`(loadWeatherUseCase.execute(city = city)).thenReturn(null)

            mockFragmentViewModel.loadWeather(city)

            val updateStateNews = mockFragmentViewModel.news.first()

            val updateState = mockFragmentViewModel.state.value as State.Loading

            advanceUntilIdle()

            assertTrue(updateStateNews is News.ShowError)
            assertFalse(updateState.isVisible)
            assertEquals(updateState.buttonText, expectedText)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_state_loading_weather_when_weather_is_not_null(){
        runTest {

            val loadCityUseCase: LoadCityUseCase = mock()
            val loadWeatherUseCase: LoadWeatherUseCase = mock()
            val validateFieldUseCase: ValidateFieldUseCase = mock()

            val mockFragmentViewModel = FragmentViewModel(
                loadCityUseCase = loadCityUseCase,
                loadWeatherUseCase = loadWeatherUseCase,
                validateFieldUseCase = validateFieldUseCase
            )

            val city = "Omsk"
            val expectedWeather = Weather(
                Location(
                    name = "Omsk",
                    region = "Omsk"
                ),
                Current(
                    condition = Condition(
                        text = "text",
                        icon = "path"
                    ),
                    temp_c = 4.0
                )
            )

            `when`(validateFieldUseCase.validate(city)).thenReturn(true)

            `when`(loadWeatherUseCase.execute(city = city)).thenReturn(expectedWeather)

            mockFragmentViewModel.loadWeather(city)

            val updateStateNews = mockFragmentViewModel.news.first()

            val updateState = mockFragmentViewModel.state.value as State.Content

            advanceUntilIdle()

            assertTrue(updateStateNews is News.NavigateForward)
            assertEquals(updateState.weather, expectedWeather)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_finally(){
        runTest {
            val loadCityUseCase: LoadCityUseCase = mock()
            val loadWeatherUseCase: LoadWeatherUseCase = mock()
            val validateFieldUseCase: ValidateFieldUseCase = mock()

            val mockFragmentViewModel = FragmentViewModel(
                loadCityUseCase = loadCityUseCase,
                loadWeatherUseCase = loadWeatherUseCase,
                validateFieldUseCase = validateFieldUseCase
            )

            val city = "Omsk"
            val expectedWeather = Weather(
                Location(
                    name = "Omsk",
                    region = "Omsk"
                ),
                Current(
                    condition = Condition(
                        text = "text",
                        icon = "path"
                    ),
                    temp_c = 4.0
                )
            )


            `when`(validateFieldUseCase.validate(city)).thenReturn(true)

            `when`(loadWeatherUseCase.execute(city = city)).thenReturn(expectedWeather)

            mockFragmentViewModel.loadWeather(city)

            advanceUntilIdle()

            val updateState = mockFragmentViewModel.state.value as State.Content

            assertEquals(updateState.weather, expectedWeather)
        }
    }
}