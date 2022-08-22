    package com.bemo.medicalservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate

    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun normalUser(view: View) {
        startActivity( Intent(this, ServiceList::class.java)  )
    }
    fun serviceProvider(view: View) {
        startActivity( Intent(this, PersonRegistration::class.java)  )

    }
}