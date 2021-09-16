package com.example.hom65adiblar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hom65adiblar.Adapters.RvAdibAdapter
import com.example.hom65adiblar.Models.Adib
import com.example.hom65adiblar.Utils.InternetData
import com.example.hom65adiblar.Utils.MySharedPrefarance
import com.example.hom65adiblar.databinding.FragmentAdibInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adib_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdibInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdibInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    lateinit var binding: FragmentAdibInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdibInfoBinding.inflate(layoutInflater)


        var adib = arguments?.getSerializable("keyAdib") as Adib

        MySharedPrefarance.init(context)
        val mL = MySharedPrefarance.obektString
        Picasso.get().load(adib.imageUri).into(binding.imageInfo)
        binding.txtNameInfo.text = adib.nameAndLastName
        binding.txtYilInfo.text = "${adib.tugilganYili} - ${adib.olganYili}"
        binding.txtInfo.text = adib.info


        var index = -1
        for (a in mL.indices) {
            if (mL[a].imageUri == adib.imageUri){
                index = a
                break
            }
        }

        if (index!=-1){
            binding.card2.setImageResource(R.drawable.ic_saqlangan_2)
        }

        binding.card2.setOnClickListener {
            var index = -1
            for (a in mL.indices) {
                if (mL[a].imageUri == adib.imageUri){
                    index = a
                    break
                }
            }
            if (index!=-1){
                mL.removeAt(index)
                MySharedPrefarance.obektString = mL
                binding.card2.setImageResource(R.drawable.ic_saqlangan_1)
            }else{
                mL.add(adib)
                MySharedPrefarance.obektString = mL
                binding.card2.setImageResource(R.drawable.ic_saqlangan_2)
            }
        }

        binding.cardBack.setOnClickListener { findNavController().popBackStack() }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                binding.txtInfo.setTextToHighlight(p0)
                binding.txtInfo.highlightColor = resources.getColor(R.color.yashil)
                binding.txtInfo.setTextHighlightColor("#00B238")
                binding.txtInfo.setCaseInsensitive(true)
                binding.txtInfo.highlight()
                return true
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        var intP = arguments?.getInt("keyInt", 0)
        if (intP != 1) {
            val rvAdibAdapter = arguments?.getSerializable("keyAdapter") as RvAdibAdapter
            rvAdibAdapter.list = InternetData.list
            rvAdibAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdibInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdibInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}