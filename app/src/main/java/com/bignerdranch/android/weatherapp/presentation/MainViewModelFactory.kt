package com.bignerdranch.android.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.weatherapp.data.repository.WeatherRepositoryImp
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase

/*
4. С Женей обсудить тему по данному паттерну и почему используем
 */
class MainViewModelFactory : ViewModelProvider.Factory {

    private val userRepository by lazy(LazyThreadSafetyMode.NONE){
        WeatherRepositoryImp()
    }
    private val loadWeatherUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoadWeatherUseCase(weatherRepository = userRepository )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            loadWeatherUseCase = loadWeatherUseCase) as T
    }
}
