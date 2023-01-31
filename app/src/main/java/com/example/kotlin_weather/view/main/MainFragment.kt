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

class MainFragment : Fragment(), OnItemViewClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var isDataSetRussian: Boolean = true
    private var adapter = MainFragmentAdapter()


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter

        adapter.setOnItemViewClickListener(this)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getWeatherFromLocalRussianSource()

        //переключение русского списка и мирового
        binding.mainFragmentFAB.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isDataSetRussian = !isDataSetRussian
                if (isDataSetRussian) {
                    viewModel.getWeatherFromLocalRussianSource()
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    viewModel.getWeatherFromLocalWorldSource()
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }

        })
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "Ошибка загрузки", Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                Snackbar.make(binding.root, "Первый этап", Snackbar.LENGTH_LONG).show()
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeatherData(appState.weatherData)
                Snackbar.make(binding.root, "Готово", Snackbar.LENGTH_LONG).show()
            }
        }
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