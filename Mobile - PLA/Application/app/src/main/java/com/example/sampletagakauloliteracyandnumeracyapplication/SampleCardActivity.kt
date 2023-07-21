package com.example.sampletagakauloliteracyandnumeracyapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SampleCardActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var flipped: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_card)
        imageView = findViewById(R.id.imageView)
        imageView.setImageResource(R.drawable.card1)

        flipped = false
        imageView.setOnClickListener {
            flipCard()
        }
    }

    private fun flipCard() {
        if (flipped) {
            imageView.setImageResource(R.drawable.card1)
            flipped = false
        } else {
            imageView.setImageResource(R.drawable.card_back)
            flipped = true
        }
    }
}
