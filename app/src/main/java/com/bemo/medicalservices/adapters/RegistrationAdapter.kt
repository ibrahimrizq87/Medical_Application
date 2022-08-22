package com.bemo.medicalservices.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bemo.medicalservices.R
import com.bemo.medicalservices.classes.RegistrationList

class RegistrationAdapter(val context:Context,val diseaseList:ArrayList<RegistrationList>)
    : RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder>() {
   private lateinit var onItemClick: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(pos:Int)
    }
fun setOnItemClickListener(listener: onItemClickListener){
    onItemClick=listener
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val view :View =LayoutInflater.from(context).inflate(R.layout.recycler_content,parent,false)
        return RegistrationViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        val currentDisease = diseaseList[position]
        holder.name.text = currentDisease.name
        holder.address.text = currentDisease.address
        holder.phone.text = currentDisease.phone
        holder.fromTo.text = "from: "+currentDisease.from+"to: "+currentDisease.to
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }
    class RegistrationViewHolder(item: View,listener: onItemClickListener) :RecyclerView.ViewHolder(item){

        val name = item.findViewById<TextView>(R.id.name)
        val address = item.findViewById<TextView>(R.id.address)
        val phone = item.findViewById<TextView>(R.id.phone)
        val fromTo = item.findViewById<TextView>(R.id.fromTo)
        init {
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
        }

    }

}