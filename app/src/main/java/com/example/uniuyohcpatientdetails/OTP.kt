package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityOtpBinding
import com.example.uniuyohcpatientdetails.databinding.ActivityPatientBinding

class OTP : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
       binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnsubmitotp.setOnClickListener {
            gottopatient()
        }
    }
    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }
}