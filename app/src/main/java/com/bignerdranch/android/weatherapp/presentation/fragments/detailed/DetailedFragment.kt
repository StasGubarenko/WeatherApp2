package com.bignerdranch.android.weatherapp.presentation.fragments.detailed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.databinding.FragmentDetailedBinding
import com.bignerdranch.android.weatherapp.presentation.fragments.State
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentsViewModelFactory
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentViewModel
import com.google.gson.annotations.Until
import kotlinx.coroutines.launch
import okhttp3.internal.Util

class DetailedFragment : Fragment() {

    private val viewModel: FragmentViewModel by activityViewModels{ FragmentsViewModelFactory() }

    private var _binding : FragmentDetailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailedBinding.inflate(inflater,container,false)

        val view = binding.root

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {state ->
                when(state){
                    is State.Loading -> Unit

                    is State.Content ->{
                        binding.nameCity.text = state.weather?.location?.name
                        binding.temp.text = state.weather?.current?.temp_c.toString()
                        binding.conditionOfWeather.text = state.weather?.current?.condition?.text
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