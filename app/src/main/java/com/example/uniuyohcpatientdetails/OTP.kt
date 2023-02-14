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
            val code = phoneAuthCredential.smsCode
            if (code != null) {
                binding.enterOTP.setText(code)
                verifyCode(code)
            }
        }
        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }
    }
    fun verifyCode(code: String) {
        val holdid = intent.getStringExtra("holdid")
        val credential = PhoneAuthProvider.getCredential(holdid.toString(), code)
        signInWithCredential(credential)
    }


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
    }
    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }
}