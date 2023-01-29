package com.example.uniuyohcpatientdetails

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast

import com.example.uniuyohcpatientdetails.databinding.ActivityUsersBinding
import com.google.android.gms.tasks.OnCompleteListener
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


        val inludedView: View = binding.confirmOtp.confirmOtp

        binding.confirmOtp.confirmOtp.visibility = INVISIBLE

        binding.loginphone.visibility = GONE

        val usertype = resources.getStringArray(R.array.users_list)
        if (binding.loginUsers != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usertype)

            binding.loginUsers.adapter = adapter


            binding.loginUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

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

        }



        binding.loginbtn.setOnClickListener {

           login()
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(applicationContext,ForgotPassword::class.java))
        }


        binding.confirmOtp.btnsubmitotp.setOnClickListener {

            if (binding.confirmOtp.enterotp.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Enter OTP", Toast.LENGTH_SHORT).show()
            } else {

                verifyCode(binding.confirmOtp.enterotp.text.toString())

            }
        }

    }

    fun login() {

        val phoneNumber: String = binding.loginphone.text.toString()
        val email: String = binding.loginemail.text.toString()
        val password: String = binding.loginPassword.text.toString()
        val users: String = binding.loginUsers.selectedItem.toString()

        if (phoneNumber.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val num = "+234$phoneNumber"
                        val ref = Firebase.firestore.collection("USERS")
                        val query = ref.whereEqualTo("Phone", "$phoneNumber")
                        query.get().addOnSuccessListener {documents->
                            for (document in documents){
                            sendVerificationCode(num)
                            Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                            gotoOtp()
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
            startActivity(Intent(applicationContext, Admin::class.java))
        }else if(binding.loginUsers.selectedItem.equals("Doctor")){
            binding.loginphone.visibility = GONE
            startActivity(Intent(applicationContext, Doctor::class.java))
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

            // when we receive the OTP it

            // contains a unique id which

            // we are storing in our string

            // which we have already created.
            holdid = s

        }

        // this method is called when user
        // receive OTP from Firebase.
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            // below line is used for getting OTP code

            // which is sent in phone auth credentials.
            val code = phoneAuthCredential.smsCode



            // checking if the code

            // is null or not.

            if (code != null) {

                // if the code is not null then

                // we are setting that code to

                // our OTP edittext field.
        binding.confirmOtp.enterotp.setText(code)



                // after setting this code

                // to OTP edittext field we

                // are calling our verifycode method.

                verifyCode(code)

            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        override fun onVerificationFailed(e: FirebaseException) {

            // displaying error message with firebase exception.
            Toast.makeText(this@Users, e.message, Toast.LENGTH_LONG).show()
        }
    }






    fun verifyCode(code: String) {

        // below line is used for getting

        // credentials from our verification id and code.
        //val credential = EmailAuthProvider.getCredential()

        val credential = PhoneAuthProvider.getCredential(holdid, code)

        // after getting credential we are

        // calling sign in method.
       signInWithCredential(credential)
    }

    //val authCredential = EmailAuthProvider.getCredential()
    fun signInWithCredential(credential: AuthCredential) {


        FirebaseAuth.getInstance().currentUser?.updatePhoneNumber(credential as PhoneAuthCredential)
            ?.addOnCompleteListener(this,
                OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful()) {
                        Log.d(ContentValues.TAG, "signInWithCredential:success")
                        Snackbar.make(
                            findViewById(android.R.id.content), "Mobile Verified Successfully.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException())
                        if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                            //mVerificationField.setError("Invalid code.");
                            Snackbar.make(
                                findViewById(android.R.id.content), "Invalid Code.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            gottopatient()
                        } else {
                            Toast.makeText(
                                applicationContext, "signInWithCredential:failure" + task.getException(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })

//        FirebaseAuth.getInstance().currentUser!!.linkWithCredential(credential).addOnCompleteListener(){ task->
//            if (task.isSuccessful){
//                Toast.makeText(applicationContext, "Login successfull", Toast.LENGTH_SHORT).show()
//                gottopatient()
//            }else{
//                Toast.makeText(applicationContext, task.exception?.message, Toast.LENGTH_SHORT).show()
//            }
//
//        }
    }




    fun gotoOtp(){
        binding.userslay.visibility = GONE
        binding.confirmOtp.confirmOtp.visibility = VISIBLE
    }


    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }


}

