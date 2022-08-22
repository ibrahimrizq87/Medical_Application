package com.bemo.medicalservices.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bemo.medicalservices.R
import com.bemo.medicalservices.ServiceList
import com.bemo.medicalservices.classes.MedicalOrderClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MedicineOrder: Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.medicine_order_fragment, container, false)
        val bundle=arguments
        database = Firebase.database.reference

        val city = bundle!!.getString("city").toString()
        val medName=view.findViewById<EditText>(R.id.mName)
        val mAddress=view.findViewById<EditText>(R.id.mAddress)
        val mPhone=view.findViewById<EditText>(R.id.mPhone)
        val mSend=view.findViewById<Button>(R.id.mSend)


        mSend.setOnClickListener {
            addDiseaseToDataBase( medName.text.toString(),mAddress.text.toString(),mPhone.text.toString(),city)
        }
        return view
    }
    private fun addDiseaseToDataBase( name:String,adress :String,Phone :String,city :String){

        if (name.isEmpty()||adress.isEmpty()||Phone.isEmpty()||city.isEmpty()){
            Toast.makeText(requireContext(),"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

        }else{
        database.child("Medicine Order").child(city).push().setValue(MedicalOrderClass(
            name+"اسم العلاج",adress+"العنوان" ,Phone+"رقم التليفون" ,city ))
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"تمت رفع عمليتك ", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), ServiceList::class.java))
            }.addOnFailureListener{
                Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}}
