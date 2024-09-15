package com.bignerdranch.android.weatherapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import com.bignerdranch.android.weatherapp.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels{MainViewModelFactory()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //Тап на кнопку
        mainBinding.searchButton.setOnClickListener {
            val city = mainBinding.search.text.toString()

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                    val weather = viewModel.loadWeather(city = city)

                    mainBinding.city.text = weather?.location?.name
                    mainBinding.conditionOfWeather.text = weather?.currentConditions?.condition?.text
                    mainBinding.temp.text = weather?.currentConditions?.temp_c.toString()
                }
            }
        }
    }
}