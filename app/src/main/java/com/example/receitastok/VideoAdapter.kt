package com.example.receitastok

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.receitastok.databinding.ActivityHomeBinding
import com.example.receitastok.databinding.ItemVideoBinding
import com.example.receitastok.model.Receita
import com.google.firebase.auth.FirebaseAuth

class VideoAdapter(
    private val listaReceitas: List<Receita>,
    private val homeBinding: ActivityHomeBinding,
    private val onVideoClick: (Receita) -> Unit,
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val receita = listaReceitas[position % listaReceitas.size]
        holder.bind(receita, homeBinding)
        holder.itemView.setOnClickListener {
            onVideoClick(receita) // Notifica o clique no vídeo
        }
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Simula loop infinito

    class VideoViewHolder(private val videoBinding: ItemVideoBinding) : RecyclerView.ViewHolder(videoBinding.root) {
        fun bind(receita: Receita, homeBinding: ActivityHomeBinding) {
            homeBinding.likeCount.text = receita.likes.size.toString()

            val userId = getUserUid()

            if(receita.likes.contains(userId)) {
                homeBinding.likeIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#0000FF"))
            } else {
                homeBinding.likeIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
            }

            val videoView: VideoView = videoBinding.videoView
            val videoUri =
                Uri.parse("android.resource://${itemView.context.packageName}/raw/${receita.videoName}")
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener { it.isLooping = true }
            videoView.start()
        }

        private fun getUserUid(): String {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val uid = currentUser.uid
                return uid
            } else {
                return ""
            }
        }
    }
}
