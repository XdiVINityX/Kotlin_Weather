package com.example.kotlin_weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
val repositoryImpl: RepositoryImpl = RepositoryImpl()) :
    ViewModel() {

        fun getLiveData():LiveData<AppState>{
            return liveDataToObserve
        }
    fun getLiveDataFromServer(){
        liveDataToObserve.postValue(AppState.Loading)
        Thread{
            sleep(4000)
            liveDataToObserve
                .postValue(AppState.Success(repositoryImpl.getWeatherFromRemoteSource()))
        }.start()
    }


}