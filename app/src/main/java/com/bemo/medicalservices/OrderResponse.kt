package com.bemo.medicalservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class OrderResponse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_response)
        val name=findViewById<TextView>(R.id.textView6)
        val name2=findViewById<TextView>(R.id.textView7)
        val name3=findViewById<TextView>(R.id.textView8)
        val name4=findViewById<TextView>(R.id.textView10)

        name.text=intent.getStringExtra("name").toString()
        name2.text=intent.getStringExtra("name2").toString()
        name3.text=intent.getStringExtra("name3").toString()
        name4.text=intent.getStringExtra("name4").toString()


    }
}