package com.rorono.weatherappavito.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment:FragmentActivity,private val listFragments:List<Fragment>):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return listFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}