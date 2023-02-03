package com.example.kotlin_weather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_weather.R
import com.example.kotlin_weather.domain.Weather
import com.example.kotlin_weather.view.OnItemViewClickListener

class MainFragmentAdapter:RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>(){

    private var weatherData:List<Weather> = listOf()
    private lateinit var listener: OnItemViewClickListener

    fun setOnItemViewClickListener(FragmentMain:OnItemViewClickListener){
       listener = FragmentMain
    }

    fun setWeatherData(data:List<Weather>){
        weatherData = data
        notifyDataSetChanged()
    }

  inner class MainViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun render(weather: Weather){
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.name
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Работает", Toast.LENGTH_LONG).show()
                listener.onItemClickNewInstanceDetail(weather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.render(weatherData[position])
    }

    override fun getItemCount(): Int {
       return weatherData.size
    }



}