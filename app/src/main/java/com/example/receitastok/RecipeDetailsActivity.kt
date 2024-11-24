package com.example.receitastok

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.receitastok.databinding.ActivityRecipeDetailsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura navegação
        binding.navDiscover.setOnClickListener {
            navigateToHome()
        }

        // Obtém o ID da receita enviado via Intent
        val receitaId = intent.getStringExtra("RECEITA_ID")
        if (receitaId.isNullOrEmpty()) {
            Toast.makeText(this, "ID da receita não fornecido.", Toast.LENGTH_SHORT).show()
            finish() // Fecha a Activity se o ID não estiver presente
            return
        }

        // Carrega os detalhes da receita
        loadRecipeDetails(receitaId)
    }

    private fun loadRecipeDetails(receitaId: String) {
        val db = Firebase.firestore

        // Usando o ID da receita passado pela Intent
        db.collection("Receitas")
            .document(receitaId) // Aqui, use o ID recebido
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val titulo = document.getString("titulo")
                    val ingredientes = document.getString("ingredientes")
                    val instrucoes = document.getString("instrucoes")
                    val imagemUrl = document.getString("imagemUrl")

                    // Atualiza os TextViews com os dados da receita
                    binding.ingredientsText.text = ingredientes ?: "Ingredientes não disponíveis"
                    binding.instructionsText.text = instrucoes ?: "Instruções não disponíveis"
                    binding.ingredientsTitle.text = titulo ?: "Título não disponível"

                    // Carrega a imagem da receita com o Coil
                    if (!imagemUrl.isNullOrEmpty()) {
                        binding.recipeImageView.load(imagemUrl) {
                            placeholder(R.drawable.placeholder_image)
                            error(R.drawable.error_image)
                            crossfade(true)
                        }
                    }
                } else {
                    Toast.makeText(this, "Receita não encontrada.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                Toast.makeText(this, "Erro ao carregar a receita.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
