package com.rorono.weatherappavito.models.localmodels

import com.rorono.weatherappavito.models.remotemodels.Hour

data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imgUrl: String,
    val hour: List<Hour>
)
