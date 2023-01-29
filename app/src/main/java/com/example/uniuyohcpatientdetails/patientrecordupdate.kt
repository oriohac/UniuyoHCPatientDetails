package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uniuyohcpatientdetails.databinding.ActivityPatientrecordupdateBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.net.IDN

class patientrecordupdate : AppCompatActivity() {
    private lateinit var binding: ActivityPatientrecordupdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientrecordupdate)
        binding = ActivityPatientrecordupdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.patientRecordUpdatebtn.setOnClickListener {
            update()
        }
    }
    fun update(){
        val Name = binding.patientUpdateName.text.toString()
        val Email = binding.patientUpdateEmail.text.toString()
        val DOB = binding.patientUpdateDOB.text.toString()
        val ID = binding.patientUpdateID.text.toString()
        val Phone = binding.patientUpdatePhone.text.toString()
        val BloodGroup = binding.patientUpdateBloodGroup.text.toString()
        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = Firebase.firestore.collection("USERS").document(uid)
        val userDetails = hashMapOf(
            "Name" to Name,
            "Email" to Email,
            "DOB" to DOB,
            "ID" to ID,
            "Phone" to Phone,
            "BloodGroup" to BloodGroup
        )
        ref.set(userDetails).addOnCompleteListener {task->
            if (task.isSuccessful){
                Snackbar.make(findViewById(android.R.id.content),"Details updated",Snackbar.LENGTH_LONG).show()
               startActivity(Intent(applicationContext, Patient::class.java))
            }
        }

    }
}