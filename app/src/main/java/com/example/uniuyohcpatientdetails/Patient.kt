package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityPatientBinding

class Patient : AppCompatActivity() {
    private lateinit var binding: ActivityPatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PatientEditRecordbtn.setOnClickListener {
            movetoeditrecords()
        }
        binding.PatientViewRecordbtn.setOnClickListener {
            viewhealthrecords()
        }
    }
    fun movetoeditrecords(){
        val intent = Intent(applicationContext, patientrecordupdate::class.java)
        startActivity(intent)
    }
    fun viewhealthrecords(){
        val intent = Intent(applicationContext, HealthRecord::class.java)
        startActivity(intent)
    }
}