package com.rorono.weatherappavito.network.repository

import com.rorono.weatherappavito.network.RetrofitInstance
import com.rorono.weatherappavito.network.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository (private val retrofit: RetrofitInstance){
    suspend fun getWeather(city:String):Result = withContext(Dispatchers.IO){
        try {
            val response = retrofit.api.getWeatherForecast(city = city)
            if (response.isSuccessful){
                return@withContext Result.Success(response.body()!!)
            }else{
                return@withContext Result.Error("Не удалось отобразить данные.Пожалуйста, провертье правильность вводимых данных")
            }
        }catch (e: Exception){
            return@withContext Result.Error("Отсутствует интернет подключение")
        }
    }
    suspend fun getWeatherSearchCity(city:String) = retrofit.api.getWeatherSearchCity(city = city)

}