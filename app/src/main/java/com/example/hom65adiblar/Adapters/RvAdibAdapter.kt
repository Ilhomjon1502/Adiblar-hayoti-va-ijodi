package com.example.hom65adiblar.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hom65adiblar.Models.Adib
import com.example.hom65adiblar.R
import com.example.hom65adiblar.Utils.MySharedPrefarance
import com.example.hom65adiblar.databinding.ItemRvAdibBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class RvAdibAdapter(val context: Context?, var list: ArrayList<Adib>, var rvClick: RvClick, var saqNoSaq:Int=0) :
    RecyclerView.Adapter<RvAdibAdapter.VH>(), Serializable {

    inner class VH(var itemRv: ItemRvAdibBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(adib: Adib, position: Int) {
            itemRv.tvNameAdibRv.text = adib.nameAndLastName
            itemRv.tvDateAdibRv.text = "(${adib.tugilganYili}-${adib.olganYili})"
            Picasso.get().load(adib.imageUri).into(itemRv.imageRvAdib)

            MySharedPrefarance.init(context)
//            println(MySharedPrefarance.obektString)
            var index = -1
            var mList = MySharedPrefarance.obektString
            for (n in mList.indices) {
                println("mList[n] = ${mList[n].imageUri} == ${adib.imageUri}")
                if (mList[n].imageUri == adib.imageUri) {
                    index = n
                    break
                }
            }
            println("index: $index")
            if (index!=-1){
                itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_2)
            }else{
                itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_1)
            }

            itemRv.root.setOnClickListener { rvClick.onCLick(adib) }
            itemRv.imageSelection.setOnClickListener {

                if (index != -1) {
//                    itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_1)
                    val l = MySharedPrefarance.obektString
                    println(""+l.removeAt(index)+"removed ")
                    MySharedPrefarance.obektString = l
                } else {
//                    itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_2)
                    val l = MySharedPrefarance.obektString
                    l.add(adib)
                    MySharedPrefarance.obektString = l
                }
                println("saqNoSaq : $saqNoSaq")
                if (saqNoSaq == 1){
                    list.remove(adib)
                    notifyItemRemoved(position)
                    notifyItemRangeRemoved(position, mList.size)
                }else{
                    notifyItemChanged(position)
                }
            }
        }
    }

    interface RvClick {
        fun onCLick(adib: Adib)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvAdibBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}