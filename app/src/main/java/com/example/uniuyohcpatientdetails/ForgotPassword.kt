package com.example.uniuyohcpatientdetails
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uniuyohcpatientdetails.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resetPasswordBtn.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val emailAddress = binding.resetPasswordET.text.toString()
        if (binding.resetPasswordET.text.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ForgotPassword, "Password reset sent", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }else{
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }
}