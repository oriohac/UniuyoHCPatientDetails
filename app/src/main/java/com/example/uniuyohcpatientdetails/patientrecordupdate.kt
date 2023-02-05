package com.example.uniuyohcpatientdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.adapters.TextViewBindingAdapter.setText
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
        ok(nameof =  intent.getStringExtra("NameString").toString())
        binding.patientRecordUpdatebtn.setOnClickListener {
            update()
        }
    }

    fun ok(nameof : String){

        nameof
        binding.patientUpdateName.setText(nameof)
    }
    fun update(){
        val patientName = intent.getStringExtra("NameString").toString()

        var bong = binding.patientUpdateName.text.toString()
        if (bong.isEmpty()){
            binding.patientUpdateName.setText(patientName)
        }else{
           bong
        }
        var Name = bong
        val DOB = binding.patientUpdateDOB.text.toString()
        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = Firebase.firestore.collection("USERS").document(uid)

        val userDetails = hashMapOf(
            "Name" to Name,
            "DOB" to DOB
        )
        ref.update(userDetails as Map<String, Any>).addOnCompleteListener { task->
            if (task.isSuccessful){
                Snackbar.make(findViewById(android.R.id.content),"Details updated",Snackbar.LENGTH_LONG).show()
               startActivity(Intent(applicationContext, Patient::class.java))
            }
        }

    }
}