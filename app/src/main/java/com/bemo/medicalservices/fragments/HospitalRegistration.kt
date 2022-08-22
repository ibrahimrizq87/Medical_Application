package com.bemo.medicalservices.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bemo.medicalservices.R
import com.bemo.medicalservices.ServiceList
import com.bemo.medicalservices.adapters.RegistrationAdapter
import com.bemo.medicalservices.classes.RegistrationList
import com.bemo.medicalservices.classes.RegistrationOrder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HospitalRegistration: Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var diseaseRecyclerView: RecyclerView
    private lateinit var diseaseList: ArrayList<RegistrationList>
    private lateinit var adapter: RegistrationAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.hospital_registration_fragment, container, false)

        val bundle=arguments
        val city = bundle!!.getString("city").toString()
        database = Firebase.database.reference
        diseaseList = ArrayList()
        adapter = RegistrationAdapter(requireContext(),diseaseList)
        diseaseRecyclerView = view.findViewById(R.id.place_recyclerView)
        diseaseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        diseaseRecyclerView.adapter = adapter
        val name=view.findViewById<EditText>(R.id.hr_name)
        val age=view.findViewById<EditText>(R.id.hr_age)
        val phone=view.findViewById<EditText>(R.id.hr_phone)
        adapter.setOnItemClickListener(object : RegistrationAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                addDiseaseToDataBase(name.text.toString(),age.text.toString(),phone.text.toString(), diseaseList[pos].name.toString())

            }

        })
        database.child("Places").child(city).child("مستشفى").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                diseaseList.clear()
                for (postSnapshot in snapshot.children){
                    val currentDisease = postSnapshot.getValue(RegistrationList::class.java)
                    diseaseList.add(currentDisease!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return view
    }
    private fun addDiseaseToDataBase( name:String,age :String,Phone :String,placeName :String){
        if (name.isEmpty()||age.isEmpty()||Phone.isEmpty()||placeName.isEmpty()){
            Toast.makeText(requireContext(),"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

        }else{
        database.child("Place Order").child(placeName).push().setValue(RegistrationOrder(
            name+"اسم المريض ",
            age +"العمر ",Phone+"رقم التليفون " ,placeName ))
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"تمت رفع عمليتك ", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), ServiceList::class.java))
            }.addOnFailureListener{
                Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_LONG).show()
            }
    }}
}