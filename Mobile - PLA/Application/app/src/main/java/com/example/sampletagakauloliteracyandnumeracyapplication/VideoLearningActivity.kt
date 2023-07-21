package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import java.io.File

class VideoLearningActivity : AppCompatActivity() {
    private lateinit var vidPlayer: VideoView

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_video_learning)

        DeviceNavigationClass.hideNavigationBar(this)

        val vidId = intent.getStringExtra("vidId")

        println(vidId)

        vidPlayer = findViewById(R.id.vidPlayer)

        val ipAddress = ConnectionClass()
        val mediaAccess = "Media/$vidId.mp4"
        val dir = ipAddress + mediaAccess

        val videoUri = Uri.parse(dir)
        vidPlayer.setVideoURI(videoUri)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(vidPlayer)
        vidPlayer.setMediaController(mediaController)

        vidPlayer.start()
    }
}
