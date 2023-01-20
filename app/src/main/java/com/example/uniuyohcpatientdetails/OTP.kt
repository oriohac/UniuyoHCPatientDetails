package com.example.uniuyohcpatientdetails
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uniuyohcpatientdetails.databinding.ActivityOtpBinding
import com.example.uniuyohcpatientdetails.databinding.ActivityPatientBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OTP : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding

    private lateinit var mAuth: FirebaseAuth

    private lateinit var phoneAuthCredential: PhoneAuthCredential


    private var verificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)





            binding.btnsubmitotp.setOnClickListener {
                if (binding.enterotp.text.toString().isEmpty()) {
                    Toast.makeText(applicationContext, "Enter OTP", Toast.LENGTH_SHORT).show()
                } else {

                   verifyCode(binding.enterotp.text.toString())

                }
            }
        }

    fun verifyCode(code: String) {

        // below line is used for getting

        // credentials from our verification id and code.

        val credential = PhoneAuthProvider.getCredential(holdid, code)

        val authCredential = EmailAuthProvider.getCredential(R.id.loginemail.toString(),R.id.loginPassword.toString())
        // after getting credential we are

        // calling sign in method.
        signInWithCredential(authCredential)
    }

    //val authCredential = EmailAuthProvider.getCredential()
    fun signInWithCredential(authCredential: AuthCredential) {

       // val authCredential = EmailAuthProvider.getCredential("","")

       FirebaseAuth.getInstance().currentUser!!.linkWithCredential(authCredential).addOnCompleteListener(){task->
           if (task.isSuccessful){
               Toast.makeText(applicationContext, "Login successfull", Toast.LENGTH_SHORT).show()
               gottopatient()
           }else{
               Toast.makeText(applicationContext, task.exception?.message, Toast.LENGTH_SHORT).show()
           }

       }
    }

    fun gottopatient(){
        val intent = Intent(applicationContext, Patient::class.java)
        startActivity(intent)
    }

}