package com.example.uniuyohcpatientdetails

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uniuyohcpatientdetails.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Admin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    lateinit var db: DocumentReference
     private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        binding  = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AdminAddUserbtn.setOnClickListener {
            createUser()
        }
    }
    fun createUser(){
        if ( binding.adminemail.text.toString().isNotEmpty() && binding.adminpassword.text.toString().isNotEmpty() && binding.adminname.text.toString().isNotEmpty() && binding.adminusers.toString().isNotEmpty() && binding.adminPhone.text.toString().isNotEmpty() ){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.adminemail.text.toString(), binding.adminpassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    store()
                }else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "not registered $message", Toast.LENGTH_LONG)
                        .show()
                    FirebaseAuth.getInstance().signOut()
                }
            }
        }else{
            Toast.makeText(applicationContext, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
fun store(){
    if ( binding.adminemail.text.toString().isNotEmpty() && binding.adminname.text.toString().isNotEmpty() && binding.adminusers.toString().isNotEmpty() && binding.adminPhone.text.toString().isNotEmpty() ){
        try {

            val id: String = FirebaseAuth.getInstance().currentUser!!.uid
            val appUsers = hashMapOf(
                "Email" to binding.adminemail.text.toString(),
                "Name" to binding.adminname.text.toString(),
                "User" to binding.adminusers.selectedItem.toString(),
                "Phone" to binding.adminPhone.text.toString(),
                "Uid" to id
            )
            val db = Firebase.firestore
            val userRef = db.collection("USERS").document(id)
            userRef.set(appUsers).addOnSuccessListener {
                Toast.makeText( applicationContext,"Details Uploaded Successfully", Toast.LENGTH_SHORT).show()
                binding.adminemail.text.clear()
                binding.adminname.text.clear()
                binding.adminpassword.text.clear()
                binding.adminPhone.text.clear()
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to Upload Details", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }else{
        Toast.makeText(applicationContext, "Fill all fields", Toast.LENGTH_SHORT).show()
    }
}



}
