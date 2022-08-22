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

class MedicalConsultationOrder: Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var text = ""
        val view = inflater.inflate(R.layout.medical_consultation_order, container, false)
        val cityList = resources.getStringArray(R.array.doctor_spicalist)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.doctor_spicalist,
            R.layout.spinner_items
        )
        val autoCom = view.findViewById<Spinner>(R.id.mc_spinner)
        autoCom.setAdapter(adapter)
        database = Firebase.database.reference

        val mc_bt = view.findViewById<Button>(R.id.mc_bt)
        val consultationET = view.findViewById<EditText>(R.id.mc_message)
        val Name = view.findViewById<EditText>(R.id.mc_name)
        val Phone = view.findViewById<EditText>(R.id.mc_phone)
        val txtx = view.findViewById<TextView>(R.id.txtx)



        autoCom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                text = cityList[p2]
txtx.text=text
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        mc_bt.setOnClickListener {
            val consultation = consultationET.text.toString()

            addDiseaseToDataBase(Name.text.toString(), Phone.text.toString(), text, consultation)
        }
        return view
    }

    private fun addDiseaseToDataBase(
        name: String,
        phone: String,
        specialization: String,
        consultation: String
    ) {

        if (name.isEmpty() || specialization.isEmpty() || phone.isEmpty() || consultation.isEmpty()) {
            Toast.makeText(requireContext(), "تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG)
                .show()

        } else {
            database.child("Medical Consultation").child(specialization).push().setValue(
                MedicalOrderClass(
                    name + "الاسم",
                    phone + "التليفون",
                    specialization + "التخصص ",
                    consultation + "الاستشارة "
                )
            )
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"تمت رفع عمليتك ", Toast.LENGTH_LONG).show()
                    startActivity(Intent(requireContext(), ServiceList::class.java))
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }
}