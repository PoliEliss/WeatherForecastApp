package com.rorono.weatherappavito.network.utils

import com.rorono.weatherappavito.models.remotemodels.Weather

sealed class Result {
    data class Success(val weather: Weather) : Result()
    data class Error(val error: String) : Result()
}