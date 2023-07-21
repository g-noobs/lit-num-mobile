package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_score)

        DeviceNavigationClass.hideNavigationBar(this)

        val lessonId = intent.getStringExtra("lessonId")
        var score = intent.getIntExtra("score", 0).toDouble()
        val totalscore = intent.getIntExtra("totalscore", 0).toDouble()

        println(score)
        println(totalscore)

        val lblScore = findViewById<TextView>(R.id.lblScore)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnHome = findViewById<Button>(R.id.btnHome)

        if (score == totalscore) {
            startActivity(Intent(applicationContext, QuizRewardActivity::class.java))
        }
        else {
            var actualscore = score/totalscore
            var percentagescore = actualscore * 100

            if(percentagescore >= 50) {
                lblScore.setText((score.toInt()).toString())
                lblScore.setTextColor(Color.parseColor("#E2B100"))
            }
            else {
                lblScore.setText((score.toInt()).toString())
                lblScore.setTextColor(Color.parseColor("#BF0707"))
            }
        }
        btnRetry.setOnClickListener {
            val intent = Intent(applicationContext, QuizActivity::class.java)
            intent.putExtra("lessonId", lessonId)
            startActivity(intent)
        }
        btnHome.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }
    }
}