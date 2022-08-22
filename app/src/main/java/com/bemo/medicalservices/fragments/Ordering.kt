package com.bemo.medicalservices.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bemo.medicalservices.R
import com.bemo.medicalservices.databinding.ActivityOrderingBinding

class Ordering : AppCompatActivity() {
    lateinit var massage2:String
    lateinit var binding: ActivityOrderingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    val massage= intent.getIntExtra("bottom",0)
         massage2= intent.getStringExtra("city").toString()

     binding=ActivityOrderingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    when(massage){
        1->         replaceFragment(MedicalConsultationOrder())
        2->        replaceFragment(MedicineOrder())
        3->        replaceFragment(DoctorOrderFragment())
        4->        replaceFragment(PhysicalTherapyOrder())
        5->        replaceFragment(NurseOrder())
        6->        replaceFragment(ClinicRegistration())
        7->        replaceFragment(HospitalRegistration())
        8->        replaceFragment(LabRegistration())
        9->        replaceFragment(Radiology())


    }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val bundle= Bundle()
        bundle.putString("city",massage2)
fragment.arguments=bundle
        fragmentTransaction.replace(R.id.fragment_holder,fragment)

    fragmentTransaction.commit()
    }
}