package com.example.receitastok

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityRecipeDetailsBinding
import com.example.receitastok.model.Receita

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura navegação
        binding.navDiscover.setOnClickListener {
            navigateToHome()
        }

        val receita = intent.getParcelableExtra("RECEITA_OBJETO", Receita::class.java)
        if (receita != null) {
            binding.ingredientsTitle.text = receita.titulo
            binding.instructionsText.text = receita.descricao

//            if (!receita.isNullOrEmpty()) {
//                binding.recipeImageView.load(imagemUrl) {
//                    placeholder(R.drawable.placeholder_image)
//                    error(R.drawable.error_image)
//                    crossfade(true)
//                }
//            }
        } else {
            finish()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
