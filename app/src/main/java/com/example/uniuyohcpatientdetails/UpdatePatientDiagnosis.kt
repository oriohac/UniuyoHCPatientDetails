package com.example.uniuyohcpatientdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uniuyohcpatientdetails.databinding.ActivityUpdatePatientDiagnosisBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UpdatePatientDiagnosis : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePatientDiagnosisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_patient_diagnosis)
        binding = ActivityUpdatePatientDiagnosisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.updatePatientDiagnosisbtn.setOnClickListener {
            diagnosisUpdate()
        }

    }

    private fun diagnosisUpdate() {
        val temperature = binding.updatePatientDiagnosisTemperature.text
        val pulseRate = binding.updatePatientDiagnosisPulseRate.text
        val respirationRate = binding.updatePatientDiagnosisRespiratoryRate.text
        val bloodPressure = binding.updatePatientDiagnosisBloodPressure.text
        val bloodPressure2 = binding.updatePatientDiagnosisBloodPressure2.text
        val symptom1 = binding.updatePatientDiagnosisSymptom1.text
        val symptom2 = binding.updatePatientDiagnosisSymptom2.text
        val symptom3 = binding.updatePatientDiagnosisSymptom3.text
        val illness = binding.updatePatientDiagnosisIllness.text
        val email = binding.updatePatientDiagnosisEmail.text
        val medication = binding.medications.text

        val calendarRef = Calendar.getInstance()
        val day = calendarRef.get(Calendar.DAY_OF_MONTH)
        val month = calendarRef.get(Calendar.MONTH)+1
        val year = calendarRef.get(Calendar.YEAR)
        val date = "$day-$month-$year"

        val db = Firebase.firestore
        val docNameRef = FirebaseAuth.getInstance().currentUser!!.email
        val diagnosisList = hashMapOf(
            "Temperature" to "$temperature°C",
            "PulseRate" to pulseRate.toString()+"bpm",
            "RespirationRate" to respirationRate.toString()+"BRpm",
            "BloodPressure" to "$bloodPressure/$bloodPressure2 mm Hg",
            "Symptom1" to symptom1.toString(),
            "Symptom2" to symptom2.toString(),
            "Symptom3" to symptom3.toString(),
            "Illness" to illness.toString(),
            "Email" to email.toString(),
            "Medication" to medication.toString(),
            "DocEmail" to docNameRef.toString(),
            "Date" to date

        )

        if (temperature.isNotEmpty() && pulseRate.isNotEmpty() && respirationRate.isNotEmpty() && bloodPressure.isNotEmpty() && bloodPressure2.isNotEmpty() && symptom1.isNotEmpty() || symptom2.isNotEmpty() || symptom3.isNotEmpty() && illness.isNotEmpty() && email.isNotEmpty() && medication.isNotEmpty() ){

            db.collection("Diagnosis").document(email.toString()).collection("diagnosis").document().set(diagnosisList).addOnCompleteListener {task->
                if (task.isSuccessful){
                    Snackbar.make(findViewById(android.R.id.content),"Diagnosis Uploaded Successfully",Snackbar.LENGTH_LONG).show()
                    temperature.clear()
                     pulseRate.clear()
                    respirationRate.clear()
                     bloodPressure.clear()
                    bloodPressure2.clear()
                     symptom1.clear()
                     symptom2.clear()
                     symptom3.clear()
                     illness.clear()
                     email.clear()
                    medication.clear()
                }
                }
            }else{
            Snackbar.make(findViewById(android.R.id.content),"One Of The Symptoms Fields and Every Other Must Be filled",Snackbar.LENGTH_LONG).show()
        }

    }
}