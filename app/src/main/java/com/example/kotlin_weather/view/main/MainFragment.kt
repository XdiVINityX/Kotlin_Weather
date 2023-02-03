package com.example.kotlin_weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.FragmentMainBinding
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.view.OnItemViewClickListener
import com.example.kotlin_weather.viewmodel.AppState
import com.example.kotlin_weather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Thread.sleep

class MainFragment : Fragment(), OnItemViewClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var isDataSetRussian: Boolean = true
    private var adapter = MainFragmentAdapter()

    private val viewModel: MainViewModel by lazy{
       ViewModelProvider(this).get(MainViewModel::class.java)
    }




    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            adapter.setOnItemViewClickListener(this@MainFragment)
            viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
                renderData(it)
            })
            showData()

            //переключение русского списка и мирового
            mainFragmentFAB.setOnClickListener {
                isDataSetRussian = !isDataSetRussian
                if (isDataSetRussian) {
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                    viewModel.getWeatherFromLocalRussianSource()
                } else {
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                    viewModel.getWeatherFromLocalWorldSource()

                }
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "Ошибка загрузки $throwable" , Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                with(binding){
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                    mainFragmentFAB.visibility = View.GONE
                    mainFragmentRecyclerView.visibility = View.GONE
                    Thread{
                        sleep(2000)
                        root.showSnackbarWithAction(R.string.longLoading,R.string.repeat)
                    }.start()
                }
            }
            is AppState.Success -> {
                with(binding){
                    mainFragmentLoadingLayout.visibility = View.GONE
                    mainFragmentFAB.visibility = View.VISIBLE
                    mainFragmentRecyclerView.visibility = View.VISIBLE
                    adapter.setWeatherData(appState.weatherData)
                   root.showSnackbarWithoutAction(R.string.ready)
                }

            }
        }
    }

    private fun showData(){
        if (isDataSetRussian) {
            viewModel.getWeatherFromLocalRussianSource()
        } else {
            viewModel.getWeatherFromLocalWorldSource()
        }
    }

    fun View.showSnackbarWithAction(stringTextId: Int, stringActionId: Int) {
       Snackbar.make(binding.root, stringTextId, Snackbar.LENGTH_INDEFINITE)
           .setAction(stringActionId) { showData() }
           .show()
    }

    fun View.showSnackbarWithoutAction(stringTextId: Int) {
        Snackbar.make(binding.root, stringTextId, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClickNewInstanceDetail(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(DetailsFragment.BUNDLE_KEY, weather)
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .add(R.id.fragment_container, DetailsFragment.newInstance(bundle))
            .commit()
    }
}