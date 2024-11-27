package com.example.receitastok
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class RegisterActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        auth = Firebase.auth


        binding?.btnSignIn?.setOnClickListener {
            val email: String = binding?.etEmail?.text.toString()
            val password: String = binding?.etPassword?.text.toString()
            val confirmPassword: String = binding?.etConfirmPassword?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if(password == confirmPassword) {
                    createUserWithEmailAndPassword(email, password)
                }
                else{
                    Toast.makeText(this@RegisterActivity, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Peencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding?.tvLogin?.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }


    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Log.d(TAG, "createUserWithEmailAndPassword:Success")
                Toast.makeText(baseContext, "Cadastrado", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent);
                //  val user = auth.currentUser
            } else{
                Log.w(TAG, "createUserWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext, "ERRO AO CADASTRAR", Toast.LENGTH_SHORT).show()
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