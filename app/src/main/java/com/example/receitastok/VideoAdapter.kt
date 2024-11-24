package com.example.receitastok

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.receitastok.databinding.ItemVideoBinding

class VideoAdapter(
    private val videoList: List<String>,
    private val onVideoClick: (String) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUri = videoList[position % videoList.size]
        holder.bind(videoUri)
        holder.itemView.setOnClickListener {
            onVideoClick(videoUri) // Notifica o clique no vídeo
        }
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Simula loop infinito

    class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(videoUri: String) {
            val videoView: VideoView = binding.videoView
            videoView.setVideoURI(Uri.parse(videoUri))
            videoView.setOnPreparedListener { it.isLooping = true }
            videoView.start()
        }
    }
}
