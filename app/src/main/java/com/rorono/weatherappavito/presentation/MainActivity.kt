package com.rorono.weatherappavito.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rorono.weatherappavito.R
import com.rorono.weatherappavito.databinding.ActivityMainBinding
import com.rorono.weatherappavito.network.RetrofitInstance
import com.rorono.weatherappavito.network.repository.Repository
import com.rorono.weatherappavito.viewmodel.WeatherViewModel
import com.rorono.weatherappavito.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, MainFragment.newInstance()).commit()
        val repository = Repository(retrofit = RetrofitInstance)
        val viewModelFactory = WeatherViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }
}