package com.rorono.weatherappavito.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rorono.weatherappavito.R
import com.rorono.weatherappavito.adapter.ClickListener
import com.rorono.weatherappavito.adapter.WeatherAdapter
import com.rorono.weatherappavito.databinding.FragmentTodayBinding
import com.rorono.weatherappavito.models.localmodels.WeatherModel
import com.rorono.weatherappavito.viewmodel.WeatherViewModel


class TodayFragment : Fragment() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: FragmentTodayBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.liveDataCurrentWeather.observe(viewLifecycleOwner){
            val listHours = getHoursList(it)
            adapter.submitList(listHours)
        }
        adapter.setOnListener(object : ClickListener {
            override fun onClick(weather: WeatherModel) {
            }
        })

    }
    private fun initRecyclerView() = with(binding) {
        rvToday.layoutManager = LinearLayoutManager(requireContext())
        adapter = WeatherAdapter()
        rvToday.adapter = adapter
    }
    private fun getHoursList(weatherItem:WeatherModel):List<WeatherModel>{
        val hoursList = weatherItem.hour
        val listHours = mutableListOf<WeatherModel>()
        for (i in hoursList){
            val item = WeatherModel(
                weatherItem.city,
                i.time.toString(),
                i.condition.text,
                i.temp_c.toString(),
                "",
                "",
                i.condition.icon.toString(),
                emptyList()
            )
            listHours.add(item)
        }
        return listHours
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodayFragment()
    }

}