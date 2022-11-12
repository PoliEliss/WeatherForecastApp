package com.rorono.weatherappavito.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rorono.weatherappavito.databinding.ItemWeatherBinding
import com.rorono.weatherappavito.models.localmodels.WeatherModel
import com.squareup.picasso.Picasso

class WeatherAdapter :
    ListAdapter<WeatherModel, WeatherAdapter.WeatherCurrentDayViewHolder>(ItemCallback()) {

    lateinit var onItemClickListener: ClickListener

    fun setOnListener(listener: ClickListener) {
        onItemClickListener = listener
    }

    inner class WeatherCurrentDayViewHolder(binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val date = binding.tvDate
        private val condition = binding.tvCondition
        private val temp = binding.tvTemp
        private val ivWeather = binding.imCondition

        fun bind(weather: WeatherModel) {
            itemView.setOnClickListener {
                onItemClickListener.onClick(weather)
            }
            date.text = weather.time
            condition.text = weather.condition
            temp.text = weather.currentTemp.ifEmpty {
                "${weather.maxTemp}/ ${weather.minTemp}"
            }
            Picasso.get().load("https:" + weather.imgUrl).into(ivWeather)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherCurrentDayViewHolder {
        val binding =
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherCurrentDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherCurrentDayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}