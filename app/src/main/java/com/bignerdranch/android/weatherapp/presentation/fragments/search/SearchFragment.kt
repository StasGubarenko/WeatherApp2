package com.bignerdranch.android.weatherapp.presentation.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
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
import com.bignerdranch.android.weatherapp.presentation.CustomAdapter
import com.bignerdranch.android.weatherapp.presentation.fragments.News
import com.bignerdranch.android.weatherapp.presentation.fragments.State
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentsViewModelFactory
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: FragmentViewModel by activityViewModels { FragmentsViewModelFactory() }
    private var _binding: FragmentSearchBinding? = null
    private lateinit var adapter: CustomAdapter
    private val binding get() = _binding!!
    private var city : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

         adapter = CustomAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        )

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
                    viewModel.loadCities(s.toString())
                    city = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        )

        binding.button.setOnClickListener {
            viewModel.loadWeather(city)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {state ->
                when(state){
                    is State.Loading ->{
                     binding.progressBar.isVisible  = state.isVisible
                     binding.button.text = getString(state.buttonText)
                    }

                    is State.Content ->{
                       val cities = viewModel.state.value as State.Content

                        adapter.getValidation(state.isValidateInputText)

                        if (state.isValidateInputText){
                            binding.search.setAdapter(adapter)
                            adapter.clear()
                            adapter.update(cities.cities)
                            adapter.addAll(cities.cities)
                        }


                        if (state.searchHint != null && state.searchColor != null){
                            binding.search.error = getString(state.searchHint)
                            binding.search.setHintTextColor(state.searchColor)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.news.collect{
                news ->
                when(news){
                    is News.ShowError ->{
                        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG)
                            .show()
                    }

                    is News.NavigateForward ->{
                        findNavController().navigate(R.id.detailedFragment)
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

