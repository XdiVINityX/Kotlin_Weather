package com.example.kotlin_weather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.FragmentDetailsBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.viewmodel.AppState
import com.example.kotlin_weather.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(){

    private val viewModel:DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
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
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

   private val localWeather : Weather by lazy {
       (arguments?.getParcelable<Weather>(BUNDLE_KEY))?: Weather()
   }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner,{
            renderData(it)
        })
        getWeather()


    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> { //FIXME
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "упс", Toast.LENGTH_SHORT).show()

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
              //  binding.mainView.visibility = View.INVISIBLE
                }
            is AppState.SuccessForDetails -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.INVISIBLE
                val weather = appState.weatherData
                saveWeatherInRoom(weather)
                setTextInValueLabel(weather)
                binding.root.showSnackbarWithoutAction(R.string.ready)
            }
            is AppState.Success -> TODO()
        }
    }

    private fun setTextInValueLabel(weather: Weather) {

        with(binding) {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}  lon ${localWeather.city.lon}"
            temperatureValue.text = weather.temperatyre.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            conditionValue.text = weather.condiotion

            imageViewHeader.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")


        }
    }

    private fun getWeather(){
        viewModel.getWeatherFromRemoteSource(localWeather.city.lat,localWeather.city.lon)
    }

    private fun saveWeatherInRoom(weather: Weather){
        viewModel.saveWeather(Weather(localWeather.city,weather.temperatyre,weather.feelsLike,weather.condiotion))
    }



    fun View.showSnackbarWithoutAction(stringTextId: Int) {
        Snackbar.make(binding.root, stringTextId, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}