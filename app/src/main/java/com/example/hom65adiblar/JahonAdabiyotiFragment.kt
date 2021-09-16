package com.example.hom65adiblar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.hom65adiblar.Adapters.RvAdibAdapter
import com.example.hom65adiblar.Models.Adib
import com.example.hom65adiblar.Utils.InternetData
import com.example.hom65adiblar.databinding.FragmentJahonAdabiyotiBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JahonAdabiyotiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JahonAdabiyotiFragment : Fragment() {
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

    lateinit var binding:FragmentJahonAdabiyotiBinding
    lateinit var rvAdibAdapter: RvAdibAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJahonAdabiyotiBinding.inflate(layoutInflater)
        var listM = ArrayList<Adib>()
        for (a in InternetData.list) {
            if (a.turi == 2){
                listM.add(a)
            }
        }
        rvAdibAdapter = RvAdibAdapter(context, listM, object : RvAdibAdapter.RvClick{
            override fun onCLick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyAdapter" to rvAdibAdapter))
            }
        })

        binding.rvMumtoz.adapter = rvAdibAdapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JahonAdabiyotiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JahonAdabiyotiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}