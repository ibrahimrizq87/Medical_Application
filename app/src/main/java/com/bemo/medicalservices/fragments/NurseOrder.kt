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

class NurseOrder: Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.nurse_order_fragment, container, false)
        val bundle=arguments
        val city = bundle!!.getString("city").toString()
        database = Firebase.database.reference


        val patName=view.findViewById<EditText>(R.id.pName)
        val patAddress=view.findViewById<EditText>(R.id.pAddress)
        val patPhone=view.findViewById<EditText>(R.id.pPhone)
        val phSend=view.findViewById<Button>(R.id.pSend)

        phSend.setOnClickListener {
            addDiseaseToDataBase( patName.text.toString(),patAddress.text.toString(),patPhone.text.toString(),city)
        }


        return view
    }
    private fun addDiseaseToDataBase( name:String,adress :String,Phone :String,city :String){

        if (name.isEmpty()||adress.isEmpty()||Phone.isEmpty()||city.isEmpty()){
            Toast.makeText(requireContext(),"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

        }else{
            database.child("Nurse Order").child(city).push().setValue(MedicalOrderClass(name+"الاسم ",adress+"العنوان "
                ,Phone+"التليفون" ,city ))
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"تمت رفع عمليتك ", Toast.LENGTH_LONG).show()
                    startActivity(Intent(requireContext(), ServiceList::class.java))
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_LONG).show()
                }
        }

    }
}

