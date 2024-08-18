package com.bignerdranch.android.weatherapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.databinding.ActivityMainBinding
import com.bignerdranch.android.weatherapp.domain.usecase.LoadWeatherUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        //Тап на кнопку
        mainBinding.searchButton.setOnClickListener {
            val city = mainBinding.search.text.toString()
            load(city = city)
        }


    }

    private fun load(city: String){
        viewModel.loadWeather(city = city)
    }

}