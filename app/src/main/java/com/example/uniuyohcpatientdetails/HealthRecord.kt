package com.example.uniuyohcpatientdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniuyohcpatientdetails.databinding.ActivityHealthRecordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HealthRecord : AppCompatActivity() {
    private lateinit var binding: ActivityHealthRecordBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DiagnosisData>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_record)
        binding = ActivityHealthRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        healthRecordValues()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

    }



    fun healthRecordValues() {
        val auth = FirebaseAuth.getInstance()
        val currentEmail = auth.currentUser!!.email
        val dbRef =
            Firebase.firestore.collection("Health Records").document(currentEmail.toString())
        dbRef.get().addOnSuccessListener {
            if (it.exists()) {
                val bloodGroup = it.getString("BloodGroup").toString()
                val genotype = it.getString("Genotype").toString()
                val disability = it.getString("Disability").toString()
                val allergy = it.getString("Allergy").toString()

                binding.tvBloodGroup.text = bloodGroup
                binding.tvGenotype.text = genotype
                binding.tvDisability.text = disability
                binding.tvAllergy.text = allergy
            }

        }
    }

    private fun EventChangeListener() {
        val auth = FirebaseAuth.getInstance()

        val currentEmail = auth.currentUser!!.email
       val db = Firebase.firestore

        db.collection("Diagnosis").document(currentEmail.toString()).collection("diagnosis").addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("Firestore Error",error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.document.toObject(DiagnosisData::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }

            })

    }
}
