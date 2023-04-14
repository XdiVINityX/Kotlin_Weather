package com.example.kotlin_weather.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.FragmentHistoryBinding
import com.example.kotlin_weather.viewmodel.AppState
import com.example.kotlin_weather.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment(){
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var _adapter : HistoryAdapter?  = null
    private val adapter get() = _adapter!!


    private val viewModel: HistoryViewModel by lazy{
       ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _adapter = HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _adapter = HistoryAdapter()
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner,{
            renderData(it)
        })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "Ошибка загрузки $throwable" , Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                with(binding){
                    loadingLayout.visibility = View.VISIBLE
                }
            }
            is AppState.Success -> {
                with(binding){
                    historyFragmentRecyclerview.adapter = adapter
                    loadingLayout.visibility = View.GONE
                    adapter.setWeatherData(appState.weatherData)
                    root.showSnackbarWithoutAction(R.string.ready)
                }
            }
            is AppState.SuccessForDetails -> {
            }

        }
    }

    fun View.showSnackbarWithoutAction(stringTextId: Int) {
        Snackbar.make(binding.root, stringTextId, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _adapter = null
    }

}