package com.bemo.medicalservices


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.bemo.medicalservices.fragments.Ordering

class ServiceList : AppCompatActivity() {
    var city = ""
    lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)
text=findViewById<TextView>(R.id.textView4)
        //val cityList= listOf<String>( "الجيزة","بنى سويف","القاهرة","اسكندرية","المنيا","اسيوط")
        val cityList = resources.getStringArray(R.array.spinnerItem)
        val adapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.spinnerItem,R.layout.spinner_items)
        val autoCom = findViewById<Spinner>(R.id.autoCompleteTextView)
        autoCom.setAdapter(adapter)

 autoCom.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
     override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) {
         text.text = cityList[p2]
         city =cityList[p2]

     }

     override fun onNothingSelected(p0: AdapterView<*>?) {

     }

 }
    }

    fun click1(view: View) {
        changeActivity(1)
    }

    private fun changeActivity(i: Int) {
        val intent =  Intent(this, Ordering::class.java).also {
            it.putExtra("bottom",i)
            it.putExtra("city",city)

        }
        if (city==""){
            text.text ="اختار بلدك الاول"
text.setTextColor(Color.RED)
        }else{startActivity(intent)}

    }

    fun click2(view: View) {  changeActivity(2)}
    fun click3(view: View) {  changeActivity(3)}
    fun click4(view: View) {  changeActivity(4)}
    fun click5(view: View) {  changeActivity(5)}
    fun click6(view: View) {  changeActivity(6)}
    fun click7(view: View) {  changeActivity(7)}
    fun click8(view: View) {  changeActivity(8)}
    fun click9(view: View) {  changeActivity(9)}
    fun regist(view: View) {startActivity(Intent(this,PersonRegistration::class.java))}
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }
}


