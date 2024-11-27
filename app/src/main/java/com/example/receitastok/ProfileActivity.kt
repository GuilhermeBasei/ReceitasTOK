package com.example.receitastok

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitastok.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserProfile()
        //setupRecipesList()

        binding.navDiscover.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupUserProfile() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email ?: "Usuário"
            val username = email.substringBefore("@") // Extrai o texto antes do '@'
            binding.tvUserName.text =
                username.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } // Coloca a primeira letra em maiúscula
            binding.tvUserEmail.text = email
        } else {
            Toast.makeText(this, "Erro ao carregar perfil", Toast.LENGTH_SHORT).show()
        }
    }

  /*  private fun setupRecipesList() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val recipesList = mutableListOf<String>()
            val adapter = RecipesAdapter(recipesList)

            binding.rvRecipes.layoutManager = LinearLayoutManager(this)
            binding.rvRecipes.adapter = adapter

            db.collection("receitas")
                .whereEqualTo("userId", currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (doc in documents) {
                        doc.getString("nome")?.let { recipesList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao carregar receitas", Toast.LENGTH_SHORT).show()
                }
        }
    }*/
}

