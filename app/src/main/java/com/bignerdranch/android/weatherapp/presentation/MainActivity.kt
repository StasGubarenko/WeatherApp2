package com.bignerdranch.android.weatherapp.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.bignerdranch.android.weatherapp.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.presentation.dialog.ErrorDialog
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

            val resultValidate = viewModel.validate(city)

            if (resultValidate){
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                        val weather = viewModel.loadWeather(city = city)

                        if (weather != null){
                            mainBinding.city.text = weather.location.name
                            mainBinding.conditionOfWeather.text = weather.currentConditions.condition.text
                            mainBinding.temp.text = weather.currentConditions.temp_c.toString()
                        }else{
                            showDialog()
                        }
                    }
                }
            } else{
                mainBinding.search.setHintTextColor(Color.RED)
                mainBinding.search.error = getString(R.string.error_message)
            }
        }
    }

    private fun showDialog(){
        ErrorDialog().show(supportFragmentManager,"ERROR_DIALOG")
    }
}