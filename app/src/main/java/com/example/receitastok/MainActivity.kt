package com.example.receitastok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityMainBinding

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth
        FirebaseFirestore.setLoggingEnabled(true)

        binding?.btnSignIn?.setOnClickListener {
            val email: String = binding?.etEmail?.text.toString()
            val password: String = binding?.etPassword?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(this@MainActivity, "Peencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding?.tvSignUp?.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent);
        }
        binding?.tvForgotPassword?.setOnClickListener{
            val intent = Intent(this, ResetActivity::class.java);
            startActivity(intent);
        }

    }


    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "singInWithEmailAndPassword:Success")
                val intent = Intent(this, HomeActivity::class.java);
                startActivity(intent);

                //val user = auth.currentUser
            } else {
                Log.w(TAG, "signInWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext, "ERRO NO LOGIN", Toast.LENGTH_SHORT).show()
            }

        }
    }


    companion object{
        private var TAG = "EmailAndPassword"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}