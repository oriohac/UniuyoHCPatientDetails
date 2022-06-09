package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_users.*

class Users : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_users)
        home.setOnClickListener {
            gohome()
        }
        admin.setOnClickListener {
            goadmin()
        }
        doctor.setOnClickListener {
            godoctor()
        }
        nurse.setOnClickListener {
            gonurse()
        }
        patient.setOnClickListener {
            gopatient()
        }
        dispenser.setOnClickListener {
            godispenser()
        }
    }



    fun gohome(){
        val intent = Intent (applicationContext, Users::class.java)
        startActivity(intent)
    }
    fun goadmin(){
        val intent = Intent (applicationContext, Admin::class.java)
        startActivity(intent)
    }
    fun godoctor() {
        val intent = Intent(applicationContext, DoctorPage::class.java)
        startActivity(intent)
    }
    fun gonurse() {
        val intent = Intent(applicationContext, Nurse::class.java)
        startActivity(intent)
    }
    fun gopatient() {
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }
    fun godispenser() {
        val intent = Intent(applicationContext, Dispenser::class.java)
        startActivity(intent)
    }

}