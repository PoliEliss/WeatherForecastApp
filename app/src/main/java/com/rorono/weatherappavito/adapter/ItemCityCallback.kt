package com.rorono.weatherappavito.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rorono.weatherappavito.models.localmodels.WeatherModel
import com.rorono.weatherappavito.models.remotemodels.SearchCityItem

class ItemCityCallback : DiffUtil.ItemCallback<SearchCityItem>() {
    override fun areItemsTheSame(oldItem: SearchCityItem, newItem: SearchCityItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SearchCityItem, newItem: SearchCityItem): Boolean {
        return oldItem == newItem
    }
}