package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityUsersBinding


class Users : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.home.setOnClickListener {
            gohome()
        }
        binding.admin.setOnClickListener {
            goadmin()
        }
        binding.doctor.setOnClickListener {
            godoctor()
        }
        binding.patient.setOnClickListener {
            login()
        }

    }



    fun gohome(){
        val intent = Intent (applicationContext, About::class.java)
        startActivity(intent)
    }
    fun goadmin(){
        val intent = Intent (applicationContext, Admin::class.java)
        startActivity(intent)
    }
    fun godoctor() {
        val intent = Intent(applicationContext, Doctor::class.java)
        startActivity(intent)
    }

    fun gopatient() {
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }
    fun login(){
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
    }

}