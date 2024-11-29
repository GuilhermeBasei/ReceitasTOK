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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class VideoAdapter(
    private val listaReceitas: List<Receita>,
    private val homeBinding: ActivityHomeBinding,
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val receita = listaReceitas[position]
        holder.bind(receita, homeBinding)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Simula loop infinito


    class VideoViewHolder(private val videoBinding: ItemVideoBinding) : RecyclerView.ViewHolder(videoBinding.root) {
        fun bind(receita: Receita, homeBinding: ActivityHomeBinding) {
            homeBinding.likeCount.text = receita.likes.size.toString()

            val userId = getUserUid()

            verificaLike(receita, homeBinding, userId)

            homeBinding.likeIcon.setOnClickListener{
                if(verificaLike(receita, homeBinding, userId)){
                    removeLike(userId,receita,homeBinding)
                }else{
                    addLike(userId,receita,homeBinding)
                }
            }

            val videoView: VideoView = videoBinding.videoView
            val videoUri =
                Uri.parse("android.resource://${itemView.context.packageName}/raw/${receita.videoName}")
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                videoView.start()
            }
            videoView.tag = "video_$position"
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

        private fun verificaLike (receita: Receita, homeBinding: ActivityHomeBinding, userId: String): Boolean {
            if(receita.likes.contains(userId)) {
                homeBinding.likeIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#FF0000"))
                return true
            } else {
                homeBinding.likeIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
                return false
            }
        }
        val db = FirebaseFirestore.getInstance()

        private fun addLike(userId: String,receita: Receita, homeBinding:ActivityHomeBinding) {
            val docRef = db.collection("Receitas").document(receita.id)

            docRef.update("likes", FieldValue.arrayUnion(userId))
                .addOnSuccessListener {
                    println("Documento Adicionado com sucesso")
                    receita.likes.add(userId)
                    homeBinding.likeCount.text = (homeBinding.likeCount.text.toString().toInt()+1).toString()
                    verificaLike(receita,homeBinding,userId)
                }
                .addOnFailureListener{ e ->
                    println("Erro ao Adicionar o documento")
                }
        }

        private fun removeLike(userId: String, receita: Receita, homeBinding:ActivityHomeBinding){
            val docRef = db.collection("Receitas").document(receita.id)

            docRef.update("likes", FieldValue.arrayRemove(userId))
                .addOnSuccessListener {
                    println("Documento excluido com sucesso")
                    receita.likes.remove(userId)
                    homeBinding.likeCount.text = (homeBinding.likeCount.text.toString().toInt()-1).toString()
                    verificaLike(receita,homeBinding,userId)
                }
                .addOnFailureListener{ e ->
                    println("Erro ao excluir o documento")
                }
        }
        }
    }

