package com.rorono.weatherappavito.viewmodel

import com.rorono.weatherappavito.models.remotemodels.Weather

sealed class WeatherState {
    data class Success(val data: Weather):WeatherState()
    data class Error(val messageError:String):WeatherState()
    object Loading:WeatherState()
}