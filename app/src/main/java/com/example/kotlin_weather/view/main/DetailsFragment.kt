package com.example.kotlin_weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.kotlin_weather.databinding.FragmentDetailsBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
        const val BUNDLE_KEY = "key"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = (arguments?.getParcelable<Weather>(BUNDLE_KEY))?: Weather()
        setTextInValueLabel(weather)
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

}