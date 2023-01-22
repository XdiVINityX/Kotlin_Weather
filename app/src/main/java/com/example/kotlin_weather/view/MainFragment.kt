package com.example.kotlin_weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.FragmentMainBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.viewmodel.AppState
import com.example.kotlin_weather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
     /*
     private val binding: FragmentMainBinding
     get() {
            return _binding!!
        }*/
    private lateinit var viewModel: MainViewModel


    companion object {
        /* fun newInstance(): Fragment {
             return MainFragment()
         }*/

        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getLiveDataFromServer()

    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error ->{
                val throwable = appState.error
            }

            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                //binding.message.text = "Загрузка"
            }
            is AppState.Success -> {
                val weather = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                setTextInValueLabel(weather)

                Snackbar.make(binding.mainView, "Готово", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setTextInValueLabel(weather: Weather) {
        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = "lat ${weather.city.lat}  lon ${weather.city.lon}"
        binding.temperatureValue.text = "${weather.temperatyre}"
        binding.feelsLikeValue.text = "${weather.feelsLike}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}