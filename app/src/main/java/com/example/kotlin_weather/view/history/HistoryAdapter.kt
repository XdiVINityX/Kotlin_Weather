package com.example.kotlin_weather.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kotlin_weather.R
import com.example.kotlin_weather.domain.Weather

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var weatherData:List<Weather> = listOf()

    fun setWeatherData(data:List<Weather>){
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.render(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class HistoryViewHolder(itemView: View) : ViewHolder(itemView){
        fun render(weather: Weather){
            itemView.findViewById<TextView>(R.id.historyFragmentRecyclerviewItem).text = weather.city.name
        }

    }
}