package com.example.receitastok

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val videoList = listOf(
            "android.resource://" + packageName + "/" + R.raw.video1,
            "android.resource://" + packageName + "/" + R.raw.video2,
            "android.resource://" + packageName + "/" + R.raw.video3
        )

        val adapter = VideoAdapter(videoList) { videoUri ->
            // Aqui você redireciona para a RecipeDetailsActivity
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra("RECEITA_ID", extractReceitaIdFromUri(videoUri)) // Extraia o ID da receita do URI, se aplicável
            startActivity(intent)
        }
        binding?.viewPager?.adapter = adapter

        binding?.profileIcon?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding?.commentIcon?.setOnClickListener {
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        binding?.viewPager?.adapter = adapter

        // Começa no meio da lista para simular loop infinito
        val startPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % videoList.size)
        binding?.viewPager?.setCurrentItem(startPosition, false)


    }
    private fun extractReceitaIdFromUri(uri: String): String {
        // Supondo que o URI seja algo como "android.resource://com.example.app/receita123"
        return uri.substringAfterLast("/") // Retorna "receita123"

    }

}
