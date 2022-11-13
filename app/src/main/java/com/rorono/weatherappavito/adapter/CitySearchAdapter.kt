package com.rorono.weatherappavito.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rorono.weatherappavito.databinding.ItemWeatherBinding
import com.rorono.weatherappavito.models.remotemodels.SearchCityItem

class CitySearchAdapter :  ListAdapter<SearchCityItem, CitySearchAdapter.SearchCityViewHolder>(ItemCityCallback()) {

    lateinit var onItemClickListener: ClickSearchCityName

    fun setOnListener(listener: ClickSearchCityName) {
        onItemClickListener = listener
    }

    inner class SearchCityViewHolder(binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val region = binding.tvDate
        private val cityName = binding.tvCondition
        private val country = binding.tvTemp
        private val imCondition = binding.imCondition

        fun bind(cityItem:SearchCityItem) {
            imCondition.visibility = View.INVISIBLE
            itemView.setOnClickListener {
               onItemClickListener.onClick(cityItem)
            }
            region.text = cityItem.region
            cityName.text = cityItem.name
            country.text = cityItem.country
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        val binding =
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}