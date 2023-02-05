package com.example.uniuyohcpatientdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uniuyohcpatientdetails.databinding.ActivityNewPatientDiagnosisBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewPatientDiagnosis : AppCompatActivity() {
    private lateinit var binding: ActivityNewPatientDiagnosisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient_diagnosis)
        binding = ActivityNewPatientDiagnosisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateHealthRecordbtn.setOnClickListener {
            updateHealthRecord()
        }
    }

   fun updateHealthRecord(){
       val dbRef = Firebase.firestore
       val bloodGroup = binding.updateHealthRecordBloodGroup.text
       val genotype = binding.updateHealthRecordGenotype.text
       val disabilities = binding.updateHealthRecordDisabilities.text
       val allergies = binding.updateHealthRecordAllergies.text
       val patientEmail = binding.updateHealthRecordEmail.text
        val record = hashMapOf(
            "BloodGroup" to bloodGroup.toString(),
            "Genotype" to genotype.toString(),
            "Disability" to disabilities.toString(),
            "Allergy" to allergies.toString(),
            "Email" to patientEmail.toString()
        )
       if (bloodGroup.isNotEmpty() && genotype.isNotEmpty() && disabilities.isNotEmpty() && allergies.isNotEmpty() && patientEmail.isNotEmpty()) {
           dbRef.collection("Health Records").document(patientEmail.toString()).set(record)
               .addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       Toast.makeText(applicationContext, "Health Record Updated Successfuly", Toast.LENGTH_SHORT).show()
                       bloodGroup.clear()
                       genotype.clear()
                       disabilities.clear()
                       allergies.clear()
                       patientEmail.clear()
                   } else {
                       Snackbar.make(findViewById(android.R.id.content),"Something Went Wrong",Snackbar.LENGTH_LONG).show()
                   }
               }
       }else{
           Snackbar.make(findViewById(android.R.id.content),"Fill all fields",Snackbar.LENGTH_LONG).show()
       }
    }
}