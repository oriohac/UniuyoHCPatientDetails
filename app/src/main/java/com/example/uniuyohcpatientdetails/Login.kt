package com.example.uniuyohcpatientdetails


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.uniuyohcpatientdetails.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


public var holdid: String = ""

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var db: DocumentReference
    private lateinit var mAuth: FirebaseAuth

    // string for storing our verification ID
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.uniuyohcpatientdetails.R.layout.activity_login)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.loginbtn.setOnClickListener {

            login()
        }

        binding.forgotPassword.setOnClickListener {
            val emailAddress = binding.loginemail.text.toString()
            if (binding.loginemail.text.isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@Login, "Password reset sent", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }else{
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun login() {

            val phoneNumber: String = binding.loginphone.text.toString()
            val email: String = binding.loginemail.text.toString()
            val password: String = binding.loginPassword.text.toString()

            if (phoneNumber.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val num = "+234$phoneNumber"
                            sendVerificationCode(num)
                            Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT)
                                .show()
                            gotoOtp()
                        } else {
                            val message = task.exception?.toString()
                            Toast.makeText(
                                applicationContext,
                                "failed: $message",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

            } else {
                Toast.makeText(this@Login, "Fill all fields", Toast.LENGTH_SHORT).show()
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
    private val mCallBack: OnVerificationStateChangedCallbacks = object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
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

            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {

                // displaying error message with firebase exception.
                Toast.makeText(this@Login, e.message, Toast.LENGTH_LONG).show()
            }
        }
    fun gotoOtp(){
        val intent = Intent(applicationContext, OTP::class.java)
        startActivity(intent)
    }

}



