package com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.weatherapp.data.repository.CityRepositoryImp
import com.bignerdranch.android.weatherapp.data.repository.WeatherRepositoryImp
import com.bignerdranch.android.weatherapp.domain.usecase.LoadCityUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase

class FragmentsViewModelFactory: ViewModelProvider.Factory {

    private val cityRepository by lazy(LazyThreadSafetyMode.NONE){
        CityRepositoryImp()
    }

    private val weatherRepository by lazy (LazyThreadSafetyMode.NONE){
        WeatherRepositoryImp()
    }
    private val loadCityUseCase: LoadCityUseCase by lazy(LazyThreadSafetyMode.NONE){
        LoadCityUseCase(cityRepository = cityRepository)
    }


    private val loadWeatherUseCase: LoadWeatherUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoadWeatherUseCase(weatherRepository = weatherRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentViewModel(
            loadCityUseCase = loadCityUseCase,
            loadWeatherUseCase = loadWeatherUseCase
        ) as T
    }
}