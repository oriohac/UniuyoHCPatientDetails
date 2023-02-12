package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, Home::class.java))
            finish()
        },3000)
    }
}