package com.example.uniuyohcpatientdetails

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import com.example.uniuyohcpatientdetails.databinding.ActivityUsersBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.util.concurrent.TimeUnit


class Users : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding

    private lateinit var holdid: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val usertype = resources.getStringArray(R.array.users_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usertype)

        binding.loginUsers.adapter = adapter


        binding.loginUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (binding.loginUsers.selectedItem.equals("Patient")){
                    binding.loginphone.visibility = VISIBLE
                }
                if (binding.loginUsers.selectedItem.equals("Admin")){
                    binding.loginphone.visibility = GONE
                }
                if (binding.loginUsers.selectedItem.equals("Doctor")){
                    binding.loginphone.visibility = GONE
                }
                if (binding.loginUsers.selectedItem.equals("Select User Type")){
                    binding.loginphone.visibility = GONE
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {

                // write code to perform some action

            }

        }



        binding.loginbtn.setOnClickListener {

           login()
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(applicationContext,ForgotPassword::class.java))
        }




    }

    fun login() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Authenticating...")
        progressDialog.setCanceledOnTouchOutside(true)

        val phoneNumber: String = binding.loginphone.text.toString()
        val email: String = binding.loginemail.text.toString()
        val password: String = binding.loginPassword.text.toString()
        val users: String = binding.loginUsers.selectedItem.toString()

        if (phoneNumber.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressDialog.show()
                    if (task.isSuccessful) {
                        val uid = FirebaseAuth.getInstance().currentUser!!.uid
                        val num = "+234$phoneNumber"
                        val ref = Firebase.firestore.collection("USERS").document(uid)

                        ref.get().addOnSuccessListener {

                            if (it.exists()){
                                val user = it.getString("User").toString()
                                val number = it.getString("Phone").toString()
                                if (user.equals("Patient") && phoneNumber.equals(number)){
                                   // progressDialog.dismiss()
                                    sendVerificationCode(num)
                                    progressDialog.dismiss()
                                   // Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                                }else if (phoneNumber != number) {
                                    progressDialog.dismiss()
                                    Snackbar.make(findViewById(android.R.id.content), "Please Enter a Corresponding Number", Snackbar.LENGTH_LONG).show()
                                } else {
                                    progressDialog.dismiss()
                                    Snackbar.make(findViewById(android.R.id.content), "Please Select Your Correct User Type", Snackbar.LENGTH_LONG).show()
                                }


                            }

                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Add the right number", Toast.LENGTH_SHORT).show()
                        }


                    } else {
                        val message = task.exception?.toString()
                        Toast.makeText(applicationContext, "failed: $message", Toast.LENGTH_LONG).show()
                    }

                }

        }else if(binding.loginUsers.selectedItem.equals("Admin")){
            binding.loginphone.visibility = GONE
            if (email.isNotEmpty() && password.isNotEmpty()){
                progressDialog.show()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task->
                        if (task.isSuccessful) {
                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
                            val dbRef = Firebase.firestore.collection("USERS").document(uid)
                            dbRef.get().addOnSuccessListener {

                                if(it.exists()){
                                    val user = it.getString("User").toString()
                                    if (user.equals("Admin")){
                                        progressDialog.dismiss()
                                        startActivity(Intent(applicationContext,Admin::class.java))
                                    }else{
                                        progressDialog.dismiss()
                                        Snackbar.make(findViewById(android.R.id.content),"Please Select Your Correct User Type",Snackbar.LENGTH_LONG).show()
                                    }
                                }

                                }

                        }else{
                            progressDialog.dismiss()
                            val message = task.exception?.toString()
                            Toast.makeText(applicationContext, "failed: $message", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        progressDialog.dismiss()
                        val message = it.toString()
                        Toast.makeText(this, "not registered $message", Toast.LENGTH_LONG)
                            .show()
                        FirebaseAuth.getInstance().signOut()
                    }
            }else{
                Snackbar.make(findViewById(android.R.id.content),"Please Fill All Fields",Snackbar.LENGTH_LONG).show()
            }


        }else if(binding.loginUsers.selectedItem.equals("Doctor")){
            binding.loginphone.visibility = GONE
            if (email.isNotEmpty() && password.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task->
                        progressDialog.show()
                        if (task.isSuccessful) {

                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
                            val dbRef = Firebase.firestore.collection("USERS").document(uid)
                            dbRef.get().addOnSuccessListener {

                                if(it.exists()){
                                    val user = it.getString("User").toString()
                                    if (user.equals("Doctor")){
                                        progressDialog.dismiss()
                                        startActivity(Intent(applicationContext,Doctor::class.java))
                                    }else{
                                        progressDialog.dismiss()
                                        Snackbar.make(findViewById(android.R.id.content),"Please Select Your Correct User Type",Snackbar.LENGTH_LONG).show()
                                    }
                                }

                            }

                        }else{
                            progressDialog.dismiss()
                            val message = task.exception?.toString()
                            Toast.makeText(applicationContext, "failed: $message", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        progressDialog.dismiss()
                        val message = it.toString()
                        Toast.makeText(this, "not registered $message", Toast.LENGTH_LONG)
                            .show()
                        FirebaseAuth.getInstance().signOut()
                    }
            }else{
                Snackbar.make(findViewById(android.R.id.content),"Please Fill All Fields",Snackbar.LENGTH_LONG).show()
            }
        }else if(binding.loginUsers.selectedItem.equals("Select User Type")){
            progressDialog.dismiss()
            Snackbar.make(findViewById(android.R.id.content),"Select User Type",Snackbar.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this@Users, "Fill all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendVerificationCode(phoneNumber: String) {

        // this method is used for getting

        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    // callback method is called on Phone auth provider.

    // initializing our callbacks for on
    // verification callback method.
    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(s, forceResendingToken)
            val progressDialog = ProgressDialog(this@Users)
            progressDialog.setTitle("Login")
            progressDialog.setMessage("Authenticating...")
            progressDialog.setCanceledOnTouchOutside(true)
            progressDialog.show()

            // when we receive the OTP it

            // contains a unique id which

            // we are storing in our string

            // which we have already created.
            holdid = s
            progressDialog.dismiss()
            startActivity(Intent(applicationContext,OTP::class.java).putExtra("holdid", holdid))
        }

        // this method is called when user
        // receive OTP from Firebase.
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {


        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        override fun onVerificationFailed(e: FirebaseException) {

            // displaying error message with firebase exception.
            Toast.makeText(this@Users, e.message, Toast.LENGTH_LONG).show()
        }

    }








    fun gotoOtp(){
//        binding.userslay.visibility = GONE
//        binding.confirmOtp.confirmOtp.visibility = VISIBLE
        startActivity(Intent(applicationContext, OTP::class.java))
    }


    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }


}

