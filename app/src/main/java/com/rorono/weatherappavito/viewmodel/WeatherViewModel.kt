package com.rorono.weatherappavito.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rorono.weatherappavito.models.localmodels.WeatherModel
import com.rorono.weatherappavito.models.remotemodels.Weather
import com.rorono.weatherappavito.network.repository.Repository
import com.rorono.weatherappavito.network.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val repository: Repository):BaseViewModel<WeatherState>() {

    val liveDataCurrentWeather = MutableLiveData<WeatherModel>()

    val liveDataListWeather = MutableLiveData<List<WeatherModel>>()

    fun getWeather(city: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.postValue(WeatherState.Loading)
                val response = repository.getTestWeather(city)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is Result.Success -> {
                            parseWeatherData(response.weather)
                            state.postValue(WeatherState.Success(response.weather))
                        }
                        is Result.Error -> {
                            state.postValue(WeatherState.Error("Ошибка"))
                        }
                    }
                }
            }
        }
    }

    private fun parseWeatherData(weather: Weather?) {

        val listDays = parseDays(weather!!)
        parseCurrentData(weather, listDays[0])

    }

    private fun parseDays(weather: Weather): List<WeatherModel> {

        val listW = ArrayList<WeatherModel>()
        val forestDays = weather.forecast.forecastday //listForecastDay
        Log.d("TEST", forestDays.toString())

        val name = weather.location.name

        for (i in forestDays.indices) {
            val day = forestDays[i]
            val item = WeatherModel(
                name,
                day.date,
                day.day.condition.text,
                "",
                day.day.maxtemp_c.toString(),
                day.day.mintemp_c.toString(),
                day.day.condition.icon,
                day.hour
            )
            listW.add(item)
        }
        liveDataListWeather.value = listW.toList()
        return listW
    }

    private fun parseCurrentData(weather: Weather?,weatherItem:WeatherModel) {
        Log.d("TEST45","$weather")
        val item = WeatherModel(
            weather?.location?.name.toString(),
            weather?.current?.last_updated.toString(),
            weather?.current?.condition?.text.toString(),
            weather?.current?.temp_c.toString(),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            weather?.current?.condition?.icon.toString(),
            weatherItem.hour
        )
        liveDataCurrentWeather.value = item
        Log.d("TEST10", "item ${liveDataCurrentWeather!!.value!!.hour.toString()}")

    }
}