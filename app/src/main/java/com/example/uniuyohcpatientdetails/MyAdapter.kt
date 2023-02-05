package com.example.uniuyohcpatientdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<DiagnosisData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        holder.temperature.text = currentitem.Temperature
        holder.pulseRate.text = currentitem.PulseRate
        holder.respirationRate.text = currentitem.RespirationRate
        holder.bloodPressure.text = currentitem.BloodPressure
        holder.symptom1.text = currentitem.Symptom1
        holder.symptom2.text = currentitem.Symptom2
        holder.symptom3.text = currentitem.Symptom3
        holder.illness.text = currentitem.Illness
        holder.medication.text = currentitem.Medication
        holder.docEmail.text = currentitem.DocEmail
        holder.date.text = currentitem.Date
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val temperature : TextView = itemView.findViewById(R.id.temperature)
        val pulseRate : TextView = itemView.findViewById(R.id.pulseRate)
        val respirationRate : TextView = itemView.findViewById(R.id.respirationRate)
        val bloodPressure : TextView = itemView.findViewById(R.id.bloodPressure)
        val symptom1 : TextView = itemView.findViewById(R.id.symptom1)
        val symptom2 : TextView = itemView.findViewById(R.id.symptom2)
        val symptom3 : TextView = itemView.findViewById(R.id.symptom3)
        val illness : TextView = itemView.findViewById(R.id.illness)
        val medication : TextView = itemView.findViewById(R.id.medications)
        val docEmail : TextView = itemView.findViewById(R.id.docEmail)
        val date : TextView = itemView.findViewById(R.id.date)
    }
}