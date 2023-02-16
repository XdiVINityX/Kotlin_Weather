package com.example.kotlin_weather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_weather.databinding.FragmentDetailsBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.repository.WeatherDTO
import com.example.kotlin_weather.repository.WeatherLoader
import com.example.kotlin_weather.repository.WeatherLoaderListener

class DetailsFragment : Fragment(), WeatherLoaderListener {


    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val weatherDTO = it.getParcelableExtra<WeatherDTO>(DETAILS_LOAD_RESULT_EXTRA)
                if (weatherDTO != null) {
                    setTextInValueLabel(weatherDTO)
                }else{
                    //TODO error
                }
            }
        }
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
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

   val localWeather : Weather by lazy {
       (arguments?.getParcelable<Weather>(BUNDLE_KEY))?: Weather()
   }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //WeatherLoader(this,localWeather.city.lat,localWeather.city.lon).loadWeather()

        val intent = Intent(requireActivity(),DetailsService::class.java)
        intent.putExtra(LATITUDE_EXTRA,localWeather.city.lat)
        intent.putExtra(LONGITUDE_EXTRA,localWeather.city.lon)

        requireActivity().startService(intent)
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(receiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onLoaded(weatherDTO: WeatherDTO) {
        setTextInValueLabel(weatherDTO)
    }
    override fun onFailed(throwable: Throwable) {
        TODO("Not yet implemented")
    }
    private fun setTextInValueLabel(weatherDTO: WeatherDTO) {
        with(binding) {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}  lon ${localWeather.city.lon}"
            temperatureValue.text = weatherDTO.fact.temp.toString()
            feelsLikeValue.text = weatherDTO.fact.feels_like.toString()
            conditionValue.text = weatherDTO.fact.condition
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}