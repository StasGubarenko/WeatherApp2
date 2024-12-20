package com.bignerdranch.android.weatherapp.presentation.fragments.search

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.databinding.FragmentSearchBinding
import com.bignerdranch.android.weatherapp.domain.models.city.City
import com.bignerdranch.android.weatherapp.presentation.CustomAdapter
import com.bignerdranch.android.weatherapp.presentation.fragments.StateCity
import com.bignerdranch.android.weatherapp.presentation.fragments.StateWeather
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

        val adapter = CustomAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
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
                        viewLifecycleOwner.lifecycleScope.launch {

                            viewModel.loadCities(input = s.toString())

                            viewModel.isSuccessResponseCity.collect {
                                when (it) {
                                    StateCity.Loading -> Unit

                                    StateCity.Success -> {
                                        val newCities = getCities(viewModel.listCity.value)

                                        adapter.clear()

                                        adapter.update(outerObject = newCities)

                                        adapter.addAll(newCities)
                                    }

                                    StateCity.Error -> {
                                        Toast.makeText(
                                            requireContext(),
                                            R.string.error,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                }
                            }
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

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loadWeather(city = inputCity)

                    viewModel.isSuccessResponseWeather.collect {
                        when (it) {
                            StateWeather.Loading -> {
                                binding.progressBar.isVisible = true
                                binding.button.text = ""
                            }

                            StateWeather.Success -> {
                                findNavController().navigate(R.id.detailedFragment)
                            }

                            StateWeather.Error -> {
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCities(geographicalFeature: List<City>): List<String> {
        val cities = mutableListOf<String>()

        for (i in geographicalFeature.indices) {
            cities.add(geographicalFeature[i].name)
        }
        return cities
    }
}
