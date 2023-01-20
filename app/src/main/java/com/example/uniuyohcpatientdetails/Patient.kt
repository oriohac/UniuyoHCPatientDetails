package com.example.uniuyohcpatientdetails

import android.app.KeyguardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.uniuyohcpatientdetails.databinding.ActivityPatientBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Patient : AppCompatActivity() {
    private lateinit var binding: ActivityPatientBinding
    private lateinit var db: DocumentReference
    private  lateinit var firestore: FirebaseFirestore
    private var cancellationSignal: CancellationSignal? = null
    private val  authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error: $errString")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Success!")
                    startActivity(Intent(this@Patient, HealthRecord::class.java))
                }
            }
    @RequiresApi(Build.VERSION_CODES.P)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        retrieveData()

        checkBiometricSupport()
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PatientEditRecordbtn.setOnClickListener {
            movetoeditrecords()
        }
        binding.PatientViewRecordbtn.setOnClickListener {
            val biometricPrompt : BiometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BiometricPrompt.Builder(this)
                    .setTitle("Title")
                    .setSubtitle("Authenticaion is required")
                    .setDescription("Fingerprint Authentication")
                    .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    }).build()
            } else {
                TODO("VERSION.SDK_INT < P")
            }
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }





    fun retrieveData(){
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser!!.uid
        val db = Firebase.firestore
        val userRef = db.collection("USERS")
        userRef.document(uid).get().addOnSuccessListener {
            if (it != null) {


                val email = it.data?.get("Email")?.toString()
                val name = it.data?.get("Name")?.toString()
                val phone = it.data?.get("Phone")?.toString()
                Log.d(TAG, "$email/$name/$phone")
                binding.patientemail.text = email
                binding.patientname.text = name
                binding.patientPhone.text = phone


        }else{

                Log.w(TAG, "Error getting documents.")
        }

        }

    }
    fun movetoeditrecords(){
        val intent = Intent(applicationContext, patientrecordupdate::class.java)
        startActivity(intent)
    }
    fun viewhealthrecords(){
        val intent = Intent(applicationContext, HealthRecord::class.java)
        startActivity(intent)
    }


    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager : KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint hs not been enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint hs not been enabled in settings.")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

}



