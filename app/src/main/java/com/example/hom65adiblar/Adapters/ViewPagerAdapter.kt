package com.example.hom65adiblar.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hom65adiblar.AdiblarListFragment
import com.example.hom65adiblar.SaqlanganAdiblarListFragment
import com.example.hom65adiblar.SettingsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val adiblarListFragment = AdiblarListFragment()
        return   when(position){

            0->{
                adiblarListFragment
            }
            1->{
                SaqlanganAdiblarListFragment(adiblarListFragment)
            }
            2->{
                SettingsFragment()
            }

            else->{
                Fragment()
            }

        }
    }
}
