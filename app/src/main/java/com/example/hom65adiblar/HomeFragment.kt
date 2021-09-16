package com.example.hom65adiblar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.hom65adiblar.Adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_tab_main.view.*

class HomeFragment : Fragment() {

    lateinit var root:View
    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        root.tab_main.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelected=tab?.position!!
                when (tab?.position) {
                    0 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_home_selected)
                    }
                    1 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_saqlanganlar_selected)
                    }
                    2 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_settings_selected)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_frame_unselected)
                    }
                    1 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_saqlanganlar_unselected)
                    }
                    2 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.ic_settings_unselected)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        //asapter\start

        val viewPager2 = root.view_pager

        val adapter= ViewPagerAdapter(childFragmentManager,lifecycle)

        viewPager2.adapter = adapter
        viewPager2.isUserInputEnabled = false

        //end

        return root
    }

    override fun onResume() {
        super.onResume()
        val tabLayout = root.tab_main
        TabLayoutMediator(tabLayout,root.view_pager){tab,position ->
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_tab_main, null, false)
            tab.customView = inflate
            when(position){
                0->{
                    inflate.image_tab_main.setImageResource(R.drawable.ic_frame_unselected)
                }
                1->{
                    inflate.image_tab_main.setImageResource(R.drawable.ic_saqlanganlar_unselected)
                }
                2->{
                    inflate.image_tab_main.setImageResource(R.drawable.ic_settings_unselected)
                }

            }
            if (tabSelected == position){
                inflate.image_tab_main.setImageResource(R.drawable.ic_adiblar_icon)
            }
        }.attach()

    }
}