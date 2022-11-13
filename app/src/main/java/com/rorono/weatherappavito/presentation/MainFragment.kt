package com.rorono.weatherappavito.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.rorono.weatherappavito.R
import com.rorono.weatherappavito.adapter.ViewPagerAdapter
import com.rorono.weatherappavito.databinding.FragmentMainBinding
import com.rorono.weatherappavito.utils.Constants
import com.rorono.weatherappavito.utils.DialogManager
import com.rorono.weatherappavito.utils.isPermissionGranted
import com.rorono.weatherappavito.viewmodel.WeatherState
import com.rorono.weatherappavito.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMainBinding
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val listFragments = listOf(
        TodayFragment.newInstance(),
        WeekFragment.newInstance(),
        SearchFragment.newInstance()
    )
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initViewPagerAdapter()
        updateCurrentWeather()
        initLocationClient()
        checkLocation()
        binding.imUpdate.setOnClickListener {
            getLocation()
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
        }

        binding.etSearchCityName.addTextChangedListener { text ->
            text?.let {
                if (it.toString().isNotEmpty()) {
                    viewModel.getSearchCity(it.toString())
                }
            }
            viewModel.observeState(viewLifecycleOwner) { state ->
                when (state) {
                    is WeatherState.Loading -> {
                        with(binding) {
                            progressBar.visibility = View.VISIBLE
                            tvCurrentTemp.visibility = View.INVISIBLE
                            tvData.visibility = View.INVISIBLE
                            imWeather.visibility = View.INVISIBLE
                            tvCityName.visibility = View.INVISIBLE
                            tvCondition.visibility = View.INVISIBLE
                            tvMinMaxTemp.visibility = View.INVISIBLE
                            etSearchCityName.visibility = View.INVISIBLE
                            tvData.visibility = View.INVISIBLE
                        }
                    }
                    is WeatherState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        with(binding) {
                            tvCurrentTemp.visibility = View.VISIBLE
                            tvData.visibility = View.VISIBLE
                            imWeather.visibility = View.VISIBLE
                            tvCityName.visibility = View.VISIBLE
                            tvCondition.visibility = View.VISIBLE
                            tvMinMaxTemp.visibility = View.VISIBLE
                            etSearchCityName.visibility = View.VISIBLE
                            tvData.visibility = View.VISIBLE
                        }
                    }
                    is WeatherState.Error -> {
                        with(binding) {
                            progressBar.visibility = View.INVISIBLE
                            tvCurrentTemp.visibility = View.VISIBLE
                            tvData.visibility = View.INVISIBLE
                            imWeather.visibility = View.INVISIBLE
                            tvCityName.visibility = View.INVISIBLE
                            tvCondition.visibility = View.VISIBLE
                            tvMinMaxTemp.visibility = View.INVISIBLE
                            etSearchCityName.visibility = View.INVISIBLE
                            tvCondition.text = getString(R.string.error_no_internet_connection)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
        getLocation()
    }

    private fun initViewPagerAdapter() = with(binding) {
        val adapter = ViewPagerAdapter(activity as FragmentActivity, listFragments)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Constants.listNameTabLayout[position]
        }.attach()
    }

    private fun initLocationClient() {
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            DialogManager.getLocationDialog(requireContext(),
                object : DialogManager.GetLocationDialogListener {
                    override fun onClickPositiveButton() {
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }

                })
        }
    }

    private fun getLocation() {
        if (!isLocationEnabled()) {
            Toast.makeText(requireContext(), getString(R.string.location_off), Toast.LENGTH_LONG)
                .show()//переделать на tv
            return
        }
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                viewModel.getWeather("${it.result.latitude},${it.result.longitude}")
            }
    }

    private fun updateCurrentWeather() = with(binding) {
        viewModel.liveDataCurrentWeather.observe(viewLifecycleOwner) {
            val maxMinTemp = "${it.maxTemp}C/${it.minTemp}C"
            tvData.text = it.time
            Picasso.get().load(getString(R.string.https) + it.imgUrl).into(imWeather)
            tvCityName.text = it.city
            tvCurrentTemp.text = it.currentTemp.ifEmpty {
                "${it.maxTemp}/${it.minTemp}"
            }
            tvCondition.text = it.condition
            tvMinMaxTemp.text = if (it.currentTemp.isEmpty()) "" else maxMinTemp
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}