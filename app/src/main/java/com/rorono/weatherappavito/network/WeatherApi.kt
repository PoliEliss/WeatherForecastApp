package com.rorono.weatherappavito.network

import com.rorono.weatherappavito.models.remotemodels.Weather
import com.rorono.weatherappavito.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json?")
    suspend fun getWeatherForecast(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") city: String,
        @Query("days") days: String = "10",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): Response<Weather>
}