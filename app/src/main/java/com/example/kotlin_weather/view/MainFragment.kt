package com.example.kotlin_weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_weather.R
import com.example.kotlin_weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    companion object {
       /* fun newInstance(): Fragment {
            return MainFragment()
        }*/

        fun newInstance() = MainFragment()
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<Any> {
            Toast.makeText(context,"I work",Toast.LENGTH_LONG).show()
        })
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

}