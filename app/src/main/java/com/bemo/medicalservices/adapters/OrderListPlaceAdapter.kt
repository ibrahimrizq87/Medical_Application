package com.bemo.medicalservices.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bemo.medicalservices.R
import com.bemo.medicalservices.classes.MedicalOrderClass
import com.bemo.medicalservices.classes.RegistrationOrder

class OrderListPlaceAdapter(val context:Context,val diseaseList:ArrayList<RegistrationOrder>)
    : RecyclerView.Adapter<OrderListPlaceAdapter.OrderListPlaceViewHolder>() {
    private lateinit var onItemClick: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(pos:Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        onItemClick=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListPlaceViewHolder {
        val view :View =LayoutInflater.from(context).inflate(R.layout.order_item,parent,false)
        return OrderListPlaceViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: OrderListPlaceViewHolder, position: Int) {
        val currentDisease = diseaseList[position]
        holder.name.text = currentDisease.name
        holder.placeName.text = currentDisease.placeName
        holder.age.text = currentDisease.age
        holder.phone.text = currentDisease.phone

    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }
    class OrderListPlaceViewHolder(item: View,listener: onItemClickListener) :RecyclerView.ViewHolder(item){

        val name = item.findViewById<TextView>(R.id.o_name)
        val placeName = item.findViewById<TextView>(R.id.o_address)
        val phone = item.findViewById<TextView>(R.id.o_phone)
        val age = item.findViewById<TextView>(R.id.value)
        init {
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
        }

    }

}