package com.rorono.weatherappavito.models.remotemodels

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)