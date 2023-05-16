package com.example.kotlin_weather.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_weather.MyApp.Companion.getHistoryDAO
import com.example.kotlin_weather.repository.LocalRepositoryIml


class HistoryViewModel(
    private val historyLiveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val historyLocalRepositoryImpl: LocalRepositoryIml = LocalRepositoryIml(getHistoryDAO())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveDataToObserve.value = AppState.Loading
        historyLiveDataToObserve.postValue(AppState.Success(historyLocalRepositoryImpl.getAllHistory()))
    }

    fun getLiveData() = historyLiveDataToObserve

}