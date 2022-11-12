package com.rorono.weatherappavito.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.tabs.TabLayoutMediator
import com.rorono.weatherappavito.R
import com.rorono.weatherappavito.adapter.ViewPagerAdapter
import com.rorono.weatherappavito.databinding.FragmentMainBinding
import com.rorono.weatherappavito.utils.Constants


class MainFragment : Fragment() {
    //private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMainBinding
    private val listFragments = listOf(TodayFragment.newInstance(),WeekFragment.newInstance())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAdapter()
    }

    private fun initViewPagerAdapter() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, listFragments)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){tab, position->
            tab.text = Constants.listNameTabLayout[position]
        }.attach()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}