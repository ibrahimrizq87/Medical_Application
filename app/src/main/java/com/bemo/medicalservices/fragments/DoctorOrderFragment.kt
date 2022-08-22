package com.bemo.medicalservices.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bemo.medicalservices.R
import com.bemo.medicalservices.ServiceList
import com.bemo.medicalservices.classes.MedicalOrderClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DoctorOrderFragment: Fragment() {
    private lateinit var database: DatabaseReference
var text=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         val view=inflater.inflate(R.layout.doctor_order_fragment, container, false)

        val bundle=arguments
        database = Firebase.database.reference

        val city = bundle!!.getString("city").toString()
        val patName=view.findViewById<EditText>(R.id.patName)
        val patAddress=view.findViewById<EditText>(R.id.patAddress)
        val patPhone=view.findViewById<EditText>(R.id.patPhone)
        val doSend=view.findViewById<Button>(R.id.patSend)
        val texxxx=view.findViewById<TextView>(R.id.texxxxx)


        val cityList = resources.getStringArray(R.array.doctor_spicalist)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(),R.array.doctor_spicalist,R.layout.spinner_items)
        val autoCom = view.findViewById<Spinner>(R.id.do_spinner)
        autoCom.setAdapter(adapter)

        autoCom.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { text =cityList[p2]
            texxxx.text=text
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        doSend.setOnClickListener {
            addDiseaseToDataBase( patName.text.toString(),patAddress.text.toString(),patPhone.text.toString(),city)
        }
        return view
    }
    private fun addDiseaseToDataBase( name:String,adress :String,Phone :String,city :String){
        if (name.isEmpty()||adress.isEmpty()||Phone.isEmpty()||city.isEmpty()){
            Toast.makeText(requireContext(),"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

        }else{
        database.child(text).child(city).push().setValue(MedicalOrderClass(
            name+"اسم المريض "
            ,adress +"العنوان ",
            Phone +"رقم التليفنو ",city ))
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"تمت رفع عمليتك ", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), ServiceList::class.java))
            }.addOnFailureListener{
                Toast.makeText(requireContext(),it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}}