package com.example.hom65adiblar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.hom65adiblar.Adapters.ViewPagerAdapter
import com.example.hom65adiblar.Adapters.ViewPagerAdapter2
import com.example.hom65adiblar.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animation = AnimationUtils.loadAnimation(this, R.anim.app_enter_anim)
        binding.consEnterApp.visibility = View.INVISIBLE
        Anim().start()

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.imageAppEnter.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.imageAppEnter.visibility = View.GONE
                binding.consEnterApp.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })


    }

    inner class Anim : Thread() {
        override fun run() {
            super.run()
            sleep(700)
            binding.imageAppEnter.startAnimation(animation)
        }
    }

    //bu metod navigation shu activityda boshlanadi va bizga homeFragmentni ochib beradi
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.my_navigation_host).navigateUp()
    }
}