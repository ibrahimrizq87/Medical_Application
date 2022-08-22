package com.bemo.medicalservices.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bemo.medicalservices.R
import com.bemo.medicalservices.classes.MedicalOrderClass

class OrderListAdapter(val context:Context,val diseaseList:ArrayList<MedicalOrderClass>)
    : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {
    private lateinit var onItemClick: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(pos:Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        onItemClick=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val view :View =LayoutInflater.from(context).inflate(R.layout.order_item,parent,false)
        return OrderListViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val currentDisease = diseaseList[position]
        holder.name.text = currentDisease.medicineName
        holder.address.text = currentDisease.userAddress
        holder.phone.text = currentDisease.phone
        holder.value.text = currentDisease.city
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }
    class OrderListViewHolder(item: View,listener: onItemClickListener) :RecyclerView.ViewHolder(item){

        val name = item.findViewById<TextView>(R.id.o_name)
        val address = item.findViewById<TextView>(R.id.o_address)
        val phone = item.findViewById<TextView>(R.id.o_phone)
        val value = item.findViewById<TextView>(R.id.value)
        init {
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
        }

    }

}