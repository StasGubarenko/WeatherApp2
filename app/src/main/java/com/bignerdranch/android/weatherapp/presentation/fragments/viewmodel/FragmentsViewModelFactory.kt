package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.weatherapp.data.repository.CityRepositoryImpl
import com.bignerdranch.android.weatherapp.data.repository.WeatherRepositoryImpl
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ValidationFieldUseCase

class FragmentsViewModelFactory : ViewModelProvider.Factory {

    private val cityRepository by lazy(LazyThreadSafetyMode.NONE) {
        CityRepositoryImpl()
    }

    private val weatherRepository by lazy(LazyThreadSafetyMode.NONE) {
        WeatherRepositoryImpl()
    }
    private val loadCityUseCase: LoadCityUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoadCityUseCase(cityRepository = cityRepository)
    }

    private val validationFieldUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ValidationFieldUseCase()
    }


    private val loadWeatherUseCase: LoadWeatherUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoadWeatherUseCase(weatherRepository = weatherRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentViewModel(
            loadCityUseCase = loadCityUseCase,
            loadWeatherUseCase = loadWeatherUseCase,
            validationFieldUseCase = validationFieldUseCase
        ) as T
    }
}