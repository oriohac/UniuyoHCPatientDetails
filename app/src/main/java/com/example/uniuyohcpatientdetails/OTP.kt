package com.example.uniuyohcpatientdetails

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uniuyohcpatientdetails.databinding.ActivityOtpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*

class OTP : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSubmitOTP.setOnClickListener {

            if (binding.enterOTP.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Enter OTP", Toast.LENGTH_SHORT).show()
            } else {

                verifyCode(binding.enterOTP.text.toString())

            }
        }
    }





    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
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
                binding.enterOTP.setText(code)


                // after setting this code

                // to OTP edittext field we

                // are calling our verifycode method.

                verifyCode(code)

            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }

    }





    fun verifyCode(code: String) {

        // below line is used for getting

        // credentials from our verification id and code.
        //val credential = EmailAuthProvider.getCredential()
        val holdid = intent.getStringExtra("holdid")

        val credential = PhoneAuthProvider.getCredential(holdid.toString(), code)

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
                        gottopatient()
                    } else {
                        Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException())
                        if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                            //mVerificationField.setError("Invalid code.");
                            Snackbar.make(
                                findViewById(android.R.id.content), "Invalid Code.",
                                Snackbar.LENGTH_SHORT
                            ).show()

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
    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }
}