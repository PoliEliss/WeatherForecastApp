package com.rorono.weatherappavito.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rorono.weatherappavito.adapter.CitySearchAdapter
import com.rorono.weatherappavito.adapter.ClickSearchCityName
import com.rorono.weatherappavito.databinding.FragmentSearchBinding
import com.rorono.weatherappavito.models.remotemodels.SearchCityItem
import com.rorono.weatherappavito.viewmodel.WeatherViewModel


class SearchFragment : Fragment() {

    private lateinit var adapterCitySearch: CitySearchAdapter
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewCity()

        viewModel.liveDataListCity.observe(viewLifecycleOwner) {
            val listCity = mutableListOf<SearchCityItem>()
            for (i in it) {
                listCity.add(i)
            }

            adapterCitySearch.submitList(listCity)
        }
        adapterCitySearch.setOnListener(object : ClickSearchCityName {
            override fun onClick(searchCityItem: SearchCityItem) {
                viewModel.getWeather(searchCityItem.name)
            }

        })
    }

    private fun initRecyclerViewCity() = with(binding) {
        rvSearchCity.layoutManager = LinearLayoutManager(requireContext())
        adapterCitySearch = CitySearchAdapter()
        rvSearchCity.adapter = adapterCitySearch
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}