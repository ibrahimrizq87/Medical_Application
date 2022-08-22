package com.bemo.medicalservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bemo.medicalservices.classes.MedicalOrderClass
import com.bemo.medicalservices.classes.RegistrationList
import com.bemo.medicalservices.classes.personClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPlace : AppCompatActivity() {
    private lateinit var database: DatabaseReference
lateinit var text:String
    lateinit var placeN:String

    lateinit  var city:String
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_place)
        val perName: String? = intent.getStringExtra("name")
        city= intent.getStringExtra("city").toString()

        uid= intent.getStringExtra("id").toString()

        database = Firebase.database.reference
        val rp_upload=findViewById<Button>(R.id.rp_upload)
        val placeName=findViewById<EditText>(R.id.placeName)
        val placePhone=findViewById<EditText>(R.id.placePhone)
        val from=findViewById<EditText>(R.id.from)
        val to=findViewById<EditText>(R.id.to)
        val tttt=findViewById<TextView>(R.id.t)
        placeN=placeName.text.toString()
        val placeAddress=findViewById<EditText>(R.id.placeAddress)

        val cityList = resources.getStringArray(R.array.place_types)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.place_types,R.layout.spinner_items)
        val autoCom = findViewById<Spinner>(R.id.registration_spinner)
        autoCom.setAdapter(adapter)

        autoCom.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { text =cityList[p2]
                tttt.text=text}
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        rp_upload.setOnClickListener {
            if (placeName.text.toString().isEmpty()||placeAddress.text.toString().isEmpty()
                ||placePhone.text.toString().isEmpty()
                ||from.text.toString().isEmpty()
                ||to.text.toString().isEmpty()){
                Toast.makeText(this,"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

            }else{
            addDiseaseToDataBase( placeName.text.toString(),placeAddress.text.toString(),placePhone.text.toString(),from.text.toString(),to.text.toString())
            addDiseaseToDataBase2(perName.toString(),placeName.text.toString(),placePhone.text.toString())
            val intent =  Intent(this, orderList::class.java).also {
                it.putExtra("placeName",placeName.text.toString())

            }
            startActivity(intent)
        }}
    }
    private fun addDiseaseToDataBase( name:String,adress :String,Phone :String,from :String,to :String){
        database.child("Places").child(city).child(text).push().setValue(RegistrationList(name,adress ,Phone ,city,from,to))
            .addOnSuccessListener {

                Toast.makeText(this,"registered", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()
            }
    }
    private fun addDiseaseToDataBase2( name:String,address :String,Phone :String){
        database.child("Persons").child(uid).setValue(personClass(name,text,address,city))
            .addOnSuccessListener {
            }.addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()
            }
    }

}