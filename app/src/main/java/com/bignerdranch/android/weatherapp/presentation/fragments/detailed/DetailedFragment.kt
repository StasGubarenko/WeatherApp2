package com.bignerdranch.android.weatherapp.presentation.fragments.detailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.databinding.FragmentDetailedBinding
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentsViewModelFactory
import com.bignerdranch.android.weatherapp.presentation.fragments.viewmodel.FragmentViewModel

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

        viewModel.weathers.observe(viewLifecycleOwner, Observer {
            binding.nameCity.text = it.location.name
            binding.temp.text = it.current.temp_c.toString()
            binding.conditionOfWeather.text = it.current.condition.text
        })

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}