package com.rorono.weatherappavito.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rorono.weatherappavito.models.localmodels.WeatherModel


class ItemCallback:DiffUtil.ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }
}
