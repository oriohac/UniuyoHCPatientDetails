package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uniuyohcpatientdetails.databinding.ActivityDoctorBinding


class Doctor : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.doctorCreateDiagnosisRecordbtn.setOnClickListener{
            moveToNewPatientdiagnosis()
        }
        binding.doctorUpdateDiagnosisecordbtn.setOnClickListener{
            moveToUpdatePatientdiagnosis()
        }
    }
    fun moveToNewPatientdiagnosis(){
        val intent = Intent (applicationContext, NewPatientDiagnosis::class.java)
        startActivity(intent)
    }
    fun moveToUpdatePatientdiagnosis(){
        val intent = Intent (applicationContext, UpdatePatientDiagnosis::class.java)
        startActivity(intent)
    }
}