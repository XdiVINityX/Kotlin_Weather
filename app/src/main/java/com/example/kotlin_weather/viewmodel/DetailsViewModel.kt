package com.example.kotlin_weather.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_weather.databinding.ActivityMainBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.repository.*
import com.example.kotlin_weather.utils.convertDtoToModel
import com.example.kotlin_weather.view.main.MainFragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Thread.sleep

class DetailsViewModel(
    private val detailsLiveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(RemoteDataSource())
):ViewModel() {

    fun getLiveData() =  detailsLiveDataToObserve

    fun getWeatherFromRemoteSource(requestLink : String){
        detailsLiveDataToObserve.value = AppState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(requestLink,callback)
    }

   private val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            val serverResponse = response.body?.string()
            if (response.isSuccessful && serverResponse != null) {
                val weatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                detailsLiveDataToObserve.postValue(AppState.Success(convertDtoToModel(weatherDTO)))

            } else{
                // TODO HW detailsLiveDataToObserve.postValue(AppState.Error("fdfd"))
            }
        }
    }

}