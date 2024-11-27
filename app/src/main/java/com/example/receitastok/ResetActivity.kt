package com.example.receitastok
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityResetBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ResetActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityResetBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.btnSendReset?.setOnClickListener {
            val email: String = binding?.etEmail?.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("ResetPassword", "Email de redefinição enviado.")
                        Toast.makeText(baseContext, "Email de redefinição enviado.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java);
                        startActivity(intent);

                    } else {
                        Log.e("ResetPassword", "Falha ao enviar email.", task.exception)
                        Toast.makeText(baseContext, "E-mail inexistente na base de dados", Toast.LENGTH_SHORT).show()
                    }
                }

        }

            binding?.tvSignUp?.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java);
                startActivity(intent);
            }





    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}