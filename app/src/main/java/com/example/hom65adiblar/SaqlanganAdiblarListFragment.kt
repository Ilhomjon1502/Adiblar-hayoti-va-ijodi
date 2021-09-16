package com.example.hom65adiblar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.hom65adiblar.Adapters.RvAdibAdapter
import com.example.hom65adiblar.Models.Adib
import com.example.hom65adiblar.Utils.InternetData
import com.example.hom65adiblar.Utils.MySharedPrefarance
import com.example.hom65adiblar.Utils.SearchCLick
import com.example.hom65adiblar.Utils.SearchViewB
import com.example.hom65adiblar.databinding.FragmentSaqlanganAdiblarListBinding
import java.util.*
import kotlin.collections.ArrayList

class SaqlanganAdiblarListFragment(val b:SearchCLick) : Fragment() , SearchCLick by b{

    lateinit var binding:FragmentSaqlanganAdiblarListBinding
    lateinit var rvAdibAdapter:RvAdibAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaqlanganAdiblarListBinding.inflate(layoutInflater)

        MySharedPrefarance.init(context)
        val mList = MySharedPrefarance.obektString


        binding.searchViewAdib.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var list = ArrayList<Adib>()
                for (adib in mList) {
                    for (i in 0 until adib.nameAndLastName?.length!!){
                        if (adib.nameAndLastName?.subSequence(0, i).toString()
                                .lowercase(Locale.getDefault()) == p0?.toLowerCase()){
                            list.add(adib)
                        }
                    }
                }
                rvAdibAdapter = RvAdibAdapter(context, list, object : RvAdibAdapter.RvClick{
                    override fun onCLick(adib: Adib) {
                        findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyInt" to 1))
                    }
                }, 1)
                binding.rvSaqlanganAdib.adapter= rvAdibAdapter
                return true
            }
        })

        binding.searchViewAdib.setOnCloseListener {
            SearchViewB.s?.visibility = View.INVISIBLE
            false
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val list = MySharedPrefarance.obektString
        rvAdibAdapter = RvAdibAdapter(context, list, object : RvAdibAdapter.RvClick{
            override fun onCLick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyInt" to 1))
            }
        }, 1)
        binding.rvSaqlanganAdib.adapter = rvAdibAdapter
    }
}