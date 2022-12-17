package com.example.uniuyohcpatientdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityAdminBinding

class Admin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        binding  = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}