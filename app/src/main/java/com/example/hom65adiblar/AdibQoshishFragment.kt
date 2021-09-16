package com.example.hom65adiblar

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.hom65adiblar.Models.Adib
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_adib_qoshish.view.*
import kotlinx.android.synthetic.main.fragment_adiblar_list.view.*

class AdibQoshishFragment : Fragment() {
    lateinit var root:View

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    private val TAG = "MainActivity"

    var imageUrl:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_adib_qoshish, container, false)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("images")

        root.back_btn.setOnClickListener {
//            val callback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//            findNavController().navigate(R.id.settingsFragment)
//            }
//            }
//            requireActivity().onBackPressed()

            findNavController().popBackStack()
        }

        root.rasm_upload.setOnClickListener {
            askPermission(Manifest.permission.READ_EXTERNAL_STORAGE){
                //all permissions already granted or just granted
                getImageContent.launch("image/*")
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if(e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }

        }
        root.btn_save_new_adib.setOnClickListener {
                val nameAndLastName = root.edt_adib_ismi.text.toString().trim()
                val tugilganYili = root.edt_yili.text.toString().trim()
                val olganYili = root.edt_vafot_etgan_yili.text.toString().trim()
                val turi = root.spinner.selectedItemPosition
                val info = root.edt_malumot.text.toString().trim()

            Toast.makeText(context, "Yuklash boshlandi", Toast.LENGTH_SHORT).show()

                if (imageUrl!="" && nameAndLastName!="" && tugilganYili!="" && olganYili!="" && info!=""){

                val adib = Adib(imageUrl, nameAndLastName, tugilganYili, olganYili, turi, info)
                firebaseFirestore.collection("adiblar")
                    .add(adib)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Muvaffaqiyatli yuklandi \n${it.id} ", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "${it.message} xatolik", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Ma'lumot yetarli emas", Toast.LENGTH_SHORT).show()
                }
        }
        return root
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->

        val m = System.currentTimeMillis()

        if (uri!=null) {
            val uploadTask = reference.child(m.toString()).putFile(uri)

            root.rasm_progress.visibility = View.VISIBLE

            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imageUri ->
                        root.img_boy.setImageURI(uri)
                        imageUrl = imageUri.toString()
                        root.rasm_progress.visibility = View.INVISIBLE
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Error \n${it.message}", Toast.LENGTH_SHORT).show()
                root.rasm_progress.visibility = View.INVISIBLE
            }
        }
    }
}