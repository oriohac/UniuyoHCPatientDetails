package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginbtn.setOnClickListener {
            gotoOtp()
        }
    }
    fun gotoOtp(){
        val intent = Intent(applicationContext, OTP::class.java)
        startActivity(intent)
    }


}