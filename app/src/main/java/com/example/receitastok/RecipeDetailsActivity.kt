package com.example.receitastok

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.R
import com.example.receitastok.databinding.ActivityRecipeDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navDiscover.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
        val receitaId = "video.mp4"

        if (receitaId != null) {
            loadRecipeDetails(receitaId)
        }
    }

    private fun loadRecipeDetails(receitaId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Receitas")
            .document(receitaId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val titulo = document.getString("titulo")
                    val ingredientes = document.getString("ingredientes")
                    val instrucoes = document.getString("instrucoes")
                    val imagemUrl = document.getString("imagemUrl")

                    binding.ingredientsText.text = ingredientes
                    binding.instructionsText.text = instrucoes
                    binding.ingredientsTitle.text = titulo

                    if (!imagemUrl.isNullOrEmpty()) {
                        loadImageFromUrl(imagemUrl, binding.recipeImageView)
                    }
                }
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        thread {
            try {
                val imageUrl = URL(url)
                val connection: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                runOnUiThread {
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    // Exibir uma imagem padrão ou um erro
                }
            }
        }
    }

}
