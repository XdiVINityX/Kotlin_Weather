package com.example.kotlin_weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {

        fun getLiveData():LiveData<Any>{
            return liveDataToObserve
        }


}