package com.rorono.weatherappavito.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rorono.weatherappavito.adapter.ClickListener
import com.rorono.weatherappavito.adapter.WeatherAdapter
import com.rorono.weatherappavito.databinding.FragmentWeekBinding
import com.rorono.weatherappavito.models.localmodels.WeatherModel
import com.rorono.weatherappavito.viewmodel.WeatherViewModel


class WeekFragment : Fragment() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: FragmentWeekBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.liveDataListWeather.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.setOnListener(object : ClickListener {
            override fun onClick(weather: WeatherModel) {
                viewModel.liveDataCurrentWeather.value = weather
            }
        })
    }

    private fun initRecyclerView() = with(binding) {
        recyclerViewWeek.layoutManager = LinearLayoutManager(requireContext())
        adapter = WeatherAdapter()
        recyclerViewWeek.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekFragment()
    }
}