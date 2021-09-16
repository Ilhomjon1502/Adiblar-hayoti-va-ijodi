package com.example.hom65adiblar

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.hom65adiblar.Adapters.ViewPagerAdapter2
import com.example.hom65adiblar.Models.Adib
import com.example.hom65adiblar.Utils.InternetData
import com.example.hom65adiblar.Utils.SearchCLick
import com.example.hom65adiblar.Utils.SearchViewB
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_adiblar_list.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_tab_category.view.*

class AdiblarListFragment : Fragment(), SearchCLick {

    lateinit var root: View

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    private val TAG = "AdibListFragment"
    lateinit var adapter2 : ViewPagerAdapter2

    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_adiblar_list, container, false)
//        root = LayoutInflater.from(container?.context).inflate(R.layout.fragment_adiblar_list, container, false)

        root.search_adib_list.setOnClickListener {
            findNavController().navigate(R.id.searchAdibFragment)
        }

        SearchViewB.s = root.tab_main

        return root
    }

    override fun onResume() {
        super.onResume()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("images")

        InternetData.list.clear()
        firebaseFirestore.collection("adiblar")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful){
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val adib = queryDocumentSnapshot.toObject(Adib::class.java)
                        InternetData.list.add(adib)
                    }
                }
                println(InternetData.list)
                adapter2 = ViewPagerAdapter2(childFragmentManager)
                root.view_pager_category.adapter = adapter2
                root.tab_category.setupWithViewPager(root.view_pager_category)
                adapter2.notifyDataSetChanged()
                setTabs()
            }
        root.tab_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelected = tab?.position!!
                val inflate = tab?.customView
                inflate?.item_tab_category_back?.background =
                    resources.getDrawable(R.drawable.ic_tab_bacground)
                inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val inflate = tab?.customView
                inflate?.item_tab_category_back?.background =null
                inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.black_2))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setTabs() {
        val tabCount = root.tab_category.tabCount

        for (i in 0 until tabCount){
            val tabView = LayoutInflater.from(context).inflate(R.layout.item_tab_category, null, false)
            val tab = root.tab_category.getTabAt(i)
            tab?.customView = tabView

            when (i) {
                0 -> {
                    tabView.txt_item_tab_category.text = "Mumtoz adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }
                1 -> {
                    tabView.txt_item_tab_category.text = "O'zbek adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }
                2 -> {
                    tabView.txt_item_tab_category.text = "Jahon adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }

            }
            if (i == tabSelected){
                tabView.item_tab_category_back.background =
                    resources.getDrawable(R.drawable.ic_tab_bacground)
                tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabSelected = 0
    }

    override fun searchOpen() {
        root.tab_main.visibility = View.INVISIBLE
        Toast.makeText(context, "Invisible", Toast.LENGTH_SHORT).show()
    }

    override fun searchClose() {
        root.tab_main.visibility = View.INVISIBLE
    }
}