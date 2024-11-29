package com.example.receitastok

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.receitastok.databinding.ActivityHomeBinding
import com.example.receitastok.model.Receita
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class HomeActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var binding: ActivityHomeBinding
    private val listaReceitas = mutableListOf<Receita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        binding.profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Configurar botão de busca
        binding.searchButton.setOnClickListener {
            showSearchDialog()
        }

        binding.navDiscover.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
        // Verificar se há um ID de receita passado pelo Intent
        val receitaId = intent.getStringExtra("RECEITA_ID")
        if (receitaId != null) {
            // Carregar receita específica
            loadRecipeById(receitaId)
        } else {
            // Carregar todas as receitas
            loadRecipes()
        }

    }

    private fun showSearchDialog() {
        val input = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Buscar Receita")
            .setMessage("Digite a tag da receita:")
            .setView(input)
            .setPositiveButton("Buscar") { _, _ ->
                val tag = input.text.toString().trim()
                if (tag.isNotEmpty()) {
                    loadRecipesByTag(tag)
                } else {
                    Toast.makeText(this, "Digite uma tag válida!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadRecipes() {
        firestore.collection("Receitas")
            .get()
            .addOnSuccessListener { documents ->
                listaReceitas.clear()
                for (document in documents) {
                    val receita = document.toObject<Receita>()
                    receita.id = document.id
                    listaReceitas.add(receita)
                }

                // Inicializar o adapter após carregar as receitas
                setupAdapter()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar receitas", Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadRecipeById(receitaId: String) {
        loadRecipes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadRecipesByTag(tag: String) {
        firestore.collection("Receitas")
            .whereArrayContains("tags", tag) // Busca onde o campo "tags" contém a tag fornecida
            .get()
            .addOnSuccessListener { documents ->
                listaReceitas.clear()
                for (document in documents) {
                    val receita = document.toObject<Receita>()
                    receita.id = document.id
                    listaReceitas.add(receita)
                }
                if (listaReceitas.isEmpty()) {
                    Toast.makeText(this, "Nenhuma receita encontrada com a tag: $tag", Toast.LENGTH_SHORT).show()
                }
                videoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao buscar receitas: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupAdapter() {
        val shuffledReceitas = listaReceitas.shuffled()

        videoAdapter = VideoAdapter(shuffledReceitas, binding)

        binding.viewPager.adapter = videoAdapter

        binding.viewPager.setCurrentItem(0, false)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val receitaAtual = shuffledReceitas[position]

                binding.commentIcon.setOnClickListener {
                    val intent = Intent(this@HomeActivity, RecipeDetailsActivity::class.java)
                    intent.putExtra("RECEITA_OBJETO", receitaAtual)
                    startActivity(intent)
                }
            }
        })
    }
}
