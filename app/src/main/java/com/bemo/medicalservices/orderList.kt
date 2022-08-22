package com.bemo.medicalservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bemo.medicalservices.adapters.OrderListAdapter
import com.bemo.medicalservices.adapters.OrderListPlaceAdapter
import com.bemo.medicalservices.classes.MedicalOrderClass
import com.bemo.medicalservices.classes.RegistrationList
import com.bemo.medicalservices.classes.RegistrationOrder
import com.bemo.medicalservices.classes.personClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class orderList : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var user :personClass
    private lateinit var database: DatabaseReference
    private lateinit var diseaseRecyclerView: RecyclerView
    private lateinit var medicalOrderList: ArrayList<MedicalOrderClass>
    private lateinit var placeOrderList: ArrayList<RegistrationOrder>
    private lateinit var medicalAdapter: OrderListAdapter
    private lateinit var placeAdapter: OrderListPlaceAdapter
    private lateinit var placeName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        auth = Firebase.auth
        user = personClass()
        placeName= intent.getStringExtra("placeName").toString()
        val currentUser = auth.currentUser
        val id =currentUser?.uid
        database = Firebase.database.reference
        medicalOrderList = ArrayList()
        medicalAdapter = OrderListAdapter(this,medicalOrderList)

        placeOrderList = ArrayList()
        placeAdapter = OrderListPlaceAdapter(this,placeOrderList)


        diseaseRecyclerView = findViewById(R.id.order_recycler)
        diseaseRecyclerView.layoutManager = LinearLayoutManager(this)
        val person_specialization=findViewById<TextView>(R.id.person_specialization)
        val person_name=findViewById<TextView>(R.id.person_name)

        medicalAdapter.setOnItemClickListener(object : OrderListAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                val intent =  Intent(this@orderList, OrderResponse::class.java).also {
                    it.putExtra("name",medicalOrderList[pos].medicineName)
                    it.putExtra("name2",medicalOrderList[pos].userAddress)
                    it.putExtra("name3",medicalOrderList[pos].phone)
                    it.putExtra("name4",medicalOrderList[pos].city)

                }
                startActivity(intent)
            }

        })
        placeAdapter.setOnItemClickListener(object : OrderListPlaceAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                val intent =  Intent(this@orderList, OrderResponse::class.java).also {
                    it.putExtra("name",placeOrderList[pos].name)
                    it.putExtra("name2",placeOrderList[pos].age)
                    it.putExtra("name3",placeOrderList[pos].phone)
                    it.putExtra("name4",placeOrderList[pos].placeName)

                }
                startActivity(intent)

            }

        })
        database.child("Persons").child(id.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue<personClass>()!!
                person_name.text= user?.name
                person_specialization.text=user?.specialization

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        date()
/*
if (user.specialization!="معمل اشاعات"||user.specialization!="معمل تحاليل طبية"
    ||user.specialization!="مستشفى"||user.specialization!="عيادة دكتور"){


}else{

}
*/

    }
    override fun onBackPressed() {
        super.onBackPressed()
    startActivity(Intent(this,MainActivity::class.java))
    }
    fun signOut(view: View) {
        Firebase.auth.signOut()
        startActivity(Intent(this,MainActivity::class.java))


    }

    private fun downloadData(message1:String,message2:String){
        diseaseRecyclerView.adapter = medicalAdapter

        database.child(message1).child(message2).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicalOrderList.clear()
                for (postSnapshot in snapshot.children){
                    val currentDisease = postSnapshot.getValue(MedicalOrderClass::class.java)
                    medicalOrderList.add(currentDisease!!)
                }
                medicalAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun downloadData2(message1:String,message2:String){
        diseaseRecyclerView.adapter = placeAdapter

        database.child(message1).child(message2).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                placeOrderList.clear()
                for (postSnapshot in snapshot.children){
                    val currentDisease = postSnapshot.getValue(RegistrationOrder::class.java)
                    placeOrderList.add(currentDisease!!)
                }
                placeAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

private fun date(){
    when(user.specialization){
        "صيدلى" ->{
            downloadData("Medicine Order",user.city.toString())
        }
        "اخصائى علاج طبيعى" ->{
            downloadData("Physical Therapy Order",user.city.toString())

        }
        "ممرض/ة" ->{
            downloadData("Nurse Order",user.city.toString())

        }
        "دكتور باطنه" ->{
            downloadData(user.specialization.toString(),user.city.toString())


        }
        "دكتور مخ و اعصاب" ->{
            downloadData(user.specialization.toString(),user.city.toString())

        }
        "دكتور عظام" ->{
            downloadData(user.specialization.toString(),user.city.toString())

        }

        "عيادة دكتور" ->{
            downloadData2("Place Order",user.phone.toString())
        }
        "مستشفى" ->{
            downloadData2("Place Order",user.phone.toString())
        }
        "معمل تحاليل طبية" ->{
            downloadData2("Place Order",user.phone.toString())
        }
        "معمل اشاعات" ->{
            downloadData2("Place Order",user.phone.toString())
        }


    }






}

    fun refresh(view: View) {
        date()
    }
}