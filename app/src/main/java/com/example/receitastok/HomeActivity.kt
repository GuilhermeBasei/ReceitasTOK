package com.example.receitastok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoList = listOf(
            "android.resource://" + packageName + "/" + R.raw.video1,
            "android.resource://" + packageName + "/" + R.raw.video2,
            "android.resource://" + packageName + "/" + R.raw.video3
        )

        val adapter = VideoAdapter(videoList) { videoUri ->
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra("RECEITA_ID", extractReceitaIdFromUri(videoUri)) // Passando o ID da receita
            startActivity(intent)
        }

        binding.viewPager.adapter = adapter

        binding.profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val startPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % videoList.size)
        binding.viewPager.setCurrentItem(startPosition, false)
    }

    private fun extractReceitaIdFromUri(uri: String): String {
        return uri.substringAfterLast("/") // Retorna o ID da receita (video1.mp4, video2.mp4, etc.)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
