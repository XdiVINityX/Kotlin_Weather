package com.example.kotlin_weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_weather.databinding.ActivityMainBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.repository.RepositoryImpl
import com.example.kotlin_weather.view.main.MainFragment
import com.google.android.material.snackbar.Snackbar
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() =  liveDataToObserve

    fun getWeatherFromLocalRussianSource() {
        getLiveDataFromLocalSource(true)
    }

    fun getWeatherFromLocalWorldSource() {
        getLiveDataFromLocalSource(false)
    }

    private fun getLiveDataFromLocalSource(isRussian: Boolean) {

            if (isRussian) {
                liveDataToObserve
                    .postValue(AppState.Success(repositoryImpl.getLocalWeatherOfRussianCities()))
            } else {
                liveDataToObserve
                    .postValue(AppState.Success(repositoryImpl.getLocalWeatherOfWorldCities()))
            }


    }

    //с эмуляцией долгой загрузки
    /*private fun getLiveDataFromLocalSource(isRussian: Boolean) {

        if (randomNumber() <= 1) {
            simulationLongLoading()
        } else {
            if (isRussian) {
                liveDataToObserve
                    .postValue(AppState.Success(repositoryImpl.getLocalWeatherOfRussianCities()))
            } else {
                liveDataToObserve
                    .postValue(AppState.Success(repositoryImpl.getLocalWeatherOfWorldCities()))
            }

        }
    }*/

   /* private fun randomNumber() : Int{
        return (0..3).random()
    }

    private fun simulationLongLoading(){
        liveDataToObserve.postValue(AppState.Loading)

    }*/



}