package com.bignerdranch.android.weatherapp.presentation.fragments.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.databinding.FragmentSearchBinding
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.domain.models.weather.Weather
import com.bignerdranch.android.weatherapp.presentation.fragments.State
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentsViewModelFactory
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: FragmentViewModel by activityViewModels{ FragmentsViewModelFactory() }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //Здесь отправляем запрос на получения городов
    private val textWatcher : TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val input = binding.search.text.toString()

            if (input.isNotEmpty()) {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        val geographicalFeature = viewModel.loadCities(input = input)

                        val cities = getCities(geographicalFeature)

                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            cities
                        )
                        binding.search.setAdapter(adapter)
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.search.threshold = 0
        binding.search.addTextChangedListener(textWatcher)

        binding.button.setOnClickListener {
            lifecycleScope.launch {

                val inputCity = binding.search.text.toString()

                val weather = viewModel.loadWeather(city = inputCity)

                handleResponse(weather)
                /**
                 * ЕСЛИ НЕ СМОГЛИ ПОЛУЧИТЬ ПРОГНОЗ ПОГОДЫ, ТО ЛОУДЕР НЕ КРУТИТСЯ И ОТОБРАЖАЕТСЯ ОШИБКА
                 */

                binding.progressBar.visibility = View.VISIBLE
                    binding.button.text =""
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCities(geographicalFeature: MutableList<City>): ArrayList<String>{
        val cities = arrayListOf<String>()

        for (i in geographicalFeature.indices){
            cities.add(geographicalFeature[i].name)
        }

        return cities
    }

    private suspend fun handleResponse(weather: Weather?) {
        val result = if (weather != null){
            State.Success(weather = weather)
        }else{
            State.Error
        }

        when(result){
            is State.Success -> {
                if (weather != null) {
                    viewModel.sendWeather(weather)
                }
                findNavController().navigate(R.id.detailedFragment)
            }
            is State.Error ->{
                Toast.makeText(requireContext(),"Ошибка",Toast.LENGTH_LONG).show()
            }
        }

    }
}