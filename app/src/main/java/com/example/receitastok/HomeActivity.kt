package com.example.receitastok
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var isPlaying = true


    private var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val videoView = findViewById<VideoView>(R.id.videoView)

        val pauseIcon = findViewById<ImageView>(R.id.playPauseIcon)


        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.brownie_receita)

        videoView.setVideoURI(videoUri)

        videoView.start()

        videoView.setOnClickListener {
            if (isPlaying) {
                videoView.pause()
                showIconWithFade(pauseIcon)
            } else {
                videoView.start()
                pauseIcon.visibility = View.GONE;

            }
            isPlaying = !isPlaying
        }

        videoView.setOnCompletionListener {
            videoView.start()
        }
    }

    private fun showIconWithFade(icon: ImageView) {
        icon.visibility = View.VISIBLE
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        icon.startAnimation(fadeIn)
    }

    private fun hideIconWithFade(icon: ImageView) {
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        icon.startAnimation(fadeOut)
        icon.postDelayed({
            icon.visibility = View.GONE
        }, 500) // Duração da animação
    }
}