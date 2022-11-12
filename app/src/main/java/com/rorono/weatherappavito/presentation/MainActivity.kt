package com.rorono.weatherappavito.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rorono.weatherappavito.R
import com.rorono.weatherappavito.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction().replace(R.id.placeholder,MainFragment.newInstance()).commit()
        setContentView(binding.root)
    }
}