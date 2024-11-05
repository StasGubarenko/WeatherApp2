package com.bignerdranch.android.weatherapp.presentation.fragments.search

import android.graphics.Color
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
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
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

    private val viewModel: FragmentViewModel by activityViewModels { FragmentsViewModelFactory() }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        var cities = arrayListOf<String>()


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cities
        )

        binding.search.setAdapter(adapter)

        binding.search.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (s.toString().isNotEmpty()) {
                        lifecycleScope.launch {

                            val geographicalFeature = viewModel.loadCities(input = s.toString())

                            cities = getCities(geographicalFeature)
                            Log.d("TESTIM", "Города после получения: ${cities.joinToString()}")

                            Log.d("TESTIM", "---------------------------------------")
                            Log.d("TESTIM", "До вызова clear cities = ${cities.size}")
                            Log.d("TESTIM", "До вызова clear adapter = ${adapter.count}")

                            adapter.clear()
                            Log.d("TESTIM", "---------------------------------------")

                            Log.d("TESTIM", "После вызова clear cities = ${cities.size}")
                            Log.d("TESTIM", "После вызова clear adapter = ${adapter.count}")

                            adapter.addAll(cities)
                            Log.d("TESTIM", "---------------------------------------")
                            Log.d(
                                "TESTIM",
                                "После вызова adapter.addAll(cities) cities = ${cities.size}"
                            )
                            Log.d(
                                "TESTIM",
                                "После вызова adapter.addAll(cities) adapter = ${adapter.count}"
                            ) // почему-то здесь 0

                            adapter.notifyDataSetChanged()

                            Log.d(
                                "TESTIM",
                                "После вызова adapter.addAll(cities) adapter = ${adapter.count}"
                            )
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        )

        binding.button.setOnClickListener {

            val inputCity = binding.search.text.toString()

            val isValid = viewModel.validate(inputCity)


            if (isValid) {


                lifecycleScope.launch {
                    viewModel.loadWeather(city = inputCity)

                    viewModel.isSuccessResponse.collect {
                        when (it) {
                            State.Loading ->{
                                binding.progressBar.isVisible = true
                                binding.button.text = ""
                            }

                            State.Success -> {

                                findNavController().navigate(R.id.detailedFragment)
                            }

                            State.Error -> {
                                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG)
                                    .show()
                                binding.progressBar.visibility = View.INVISIBLE
                                binding.button.text = getText(R.string.search_button)
                            }
                        }
                    }
                }
            } else {
                binding.search.setHintTextColor(Color.RED)
                binding.search.error = getString(R.string.error_message)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCities(geographicalFeature: List<City>): ArrayList<String> {
        val cities = arrayListOf<String>()

        for (i in geographicalFeature.indices) {
            cities.add(geographicalFeature[i].name)
        }
        return cities
    }
}
