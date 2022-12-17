package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityDoctorsPatientFormBinding

class doctors_patient_form : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorsPatientFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_patient_form)
        binding = ActivityDoctorsPatientFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnnexttodisf.setOnClickListener {

        }
    }
}