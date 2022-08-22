package com.bemo.medicalservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bemo.medicalservices.classes.RegistrationOrder
import com.bemo.medicalservices.classes.personClass
import com.bemo.medicalservices.fragments.Ordering
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PersonRegistration : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    lateinit var text:String
    var city:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_registration)
        database = Firebase.database.reference
        auth = Firebase.auth

        val rp_upload=findViewById<Button>(R.id.bt_reg)
        val pr_email=findViewById<EditText>(R.id.pr_email)
        val pr_name=findViewById<EditText>(R.id.pr_name)
        val pr_phone=findViewById<EditText>(R.id.editText)
        val logIn=findViewById<TextView>(R.id.logIn_text)
        val text_specialization=findViewById<TextView>(R.id.textView2)
        val text_city=findViewById<TextView>(R.id.ttttt)

        val pr_password=findViewById<EditText>(R.id.pr_password)

        val cityList = resources.getStringArray(R.array.person_types)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.person_types,R.layout.spinner_items)
        val autoCom = findViewById<Spinner>(R.id.personal_registration_spinner)
        autoCom.setAdapter(adapter)
        val cityList2 = resources.getStringArray(R.array.spinnerItem)
        val adapter2: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.spinnerItem,R.layout.spinner_items)
        val autoCom2 = findViewById<Spinner>(R.id.city_spinner)
        autoCom2.setAdapter(adapter2)
        logIn.setOnClickListener {
            startActivity(Intent(this,LogIn::class.java))
        }
        autoCom.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { text =cityList[p2]
            text_specialization.text=text}
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        autoCom2.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { city =cityList2[p2]
            text_city.text=city}
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        rp_upload.setOnClickListener {
            if (pr_password.text.length<6){
                Toast.makeText(this,"تأكد من ان كلمة السر اكثر من 6 حروف", Toast.LENGTH_LONG).show()

            }else{
            auth.createUserWithEmailAndPassword(pr_email.text.toString(), pr_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val id= user?.uid
                        if (text != "مكان خدمة"){
                            addDiseaseToDataBase( pr_name.text.toString(),text,pr_phone.text.toString(),city )
                            startActivity( Intent(this, orderList::class.java)  )
                        }else{
                            val intent =  Intent(this, RegisterPlace::class.java).also {
                                it.putExtra("id",id)
                                it.putExtra("city",city)
                                it.putExtra("name",pr_name.text.toString())


                            }
                            if (pr_name.text.toString().isEmpty()||pr_phone.text.toString().isEmpty()||city.isEmpty()){
                                Toast.makeText(this,"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

                            }else{
                            startActivity(intent)
                        }
                        }


                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }}
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val id=currentUser.uid
            val intent =  Intent(this, orderList::class.java).also {
                it.putExtra("id",id)

            }
            startActivity( intent)


        }

    }
    private fun addDiseaseToDataBase( name:String,specialization :String,Phone :String,city :String){
        val currentUser = auth.currentUser
val id=currentUser?.uid
        if (name.isEmpty()||specialization.isEmpty()||Phone.isEmpty()||city.isEmpty()){
            Toast.makeText(this,"تأكد من كتابة كل البيانات اولان", Toast.LENGTH_LONG).show()

        }else{
        database.child("Persons").child(id.toString()).setValue(personClass(name,specialization ,Phone ,city ))
            .addOnSuccessListener {
                Toast.makeText(this,"Done", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}}