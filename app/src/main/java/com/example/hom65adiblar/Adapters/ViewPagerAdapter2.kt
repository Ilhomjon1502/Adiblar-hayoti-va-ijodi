package com.example.hom65adiblar.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hom65adiblar.*

class ViewPagerAdapter2(fragmentManager1: FragmentManager?): FragmentPagerAdapter(fragmentManager1!!) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> MumtozAdabiyotFragment()
            1-> OzbekAdabiyotiFragment()
            2-> JahonAdabiyotiFragment()
            else -> MumtozAdabiyotFragment()
        }
    }

}