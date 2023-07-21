package com.example.sampletagakauloliteracyandnumeracyapplication

import com.bumptech.glide.Glide
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

class ImageLearningActivity : AppCompatActivity() {
    private lateinit var words: Array<String>
    private lateinit var buttons: Array<Button>
    private lateinit var correctWord: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressSharedPref: SharedPreferences
    private lateinit var mediaSharedPreferences: SharedPreferences
    private lateinit var imgSubject: ImageView
    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_image_learning)
        DeviceNavigationClass.hideNavigationBar(this)
        sharedPreferences = getSharedPreferences("progressSubTopicPref", Context.MODE_PRIVATE)
        progressSharedPref = getSharedPreferences("progressPref", Context.MODE_PRIVATE)
        mediaSharedPreferences = getSharedPreferences("mediaSharedPref", Context.MODE_PRIVATE)
        val word = intent.getStringExtra("word")
        val lessonId = intent.getStringExtra("lessonId")
        val getSubTopicId = intent.getStringExtra("subTopicId")
        val getMediaId = mediaSharedPreferences.getString(getSubTopicId, "")

        getImageFromPHP(getMediaId.toString())

        words = arrayOf("Word1", "Word2", "Word3")
        correctWord = word.toString()

        buttons = arrayOf( findViewById(R.id.btnChoice1), findViewById(R.id.btnChoice2), findViewById(R.id.btnChoice3))

        randomizeButtonLabels()

        buttons[0].setOnClickListener {
            handleButtonClick(buttons[0], correctWord, lessonId.toString(), getSubTopicId.toString())
        }

        buttons[1].setOnClickListener {
            handleButtonClick(buttons[1], correctWord, lessonId.toString(), getSubTopicId.toString())
        }

        buttons[2].setOnClickListener {
            handleButtonClick(buttons[2], correctWord,lessonId.toString(), getSubTopicId.toString())
        }
    }

    private fun getImageFromPHP(getMediaId: String) {
        val ipAddress = ConnectionClass()
        val mediaAccess = "Media/$getMediaId.jpg"
        val dir = ipAddress + mediaAccess

        imgSubject = findViewById(R.id.imgItem)
        Log.d("URL", dir)

        Glide.with(this@ImageLearningActivity)
            .load(dir)
            .into(imgSubject)
    }

    private fun randomizeButtonLabels() {
        val wordList = mutableListOf(*words)
        wordList.shuffle()

        val correctWordIndex = Random().nextInt(buttons.size)
        wordList.add(correctWordIndex, correctWord)

        for (i in buttons.indices) {
            buttons[i].text = wordList[i]
        }
    }

    private fun handleButtonClick(button: Button, word: String, lessonId: String, getSubTopicId: String) {
        val editor = sharedPreferences.edit()
        val editor2 = progressSharedPref.edit()

        val selectedWord = button.text.toString()

        if (selectedWord == correctWord) {
            Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LearningActivity::class.java)

            val currentProg = progressSharedPref.getInt(word, 0)
            editor2.putInt(lessonId, currentProg + 1)
            editor2.putInt(word, 1)
            editor2.apply()

            intent.putExtra("lessonId", lessonId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Try again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ImageLearningActivity::class.java)
            intent.putExtra("word", word)
            intent.putExtra("subTopicId", getSubTopicId)
            intent.putExtra("lessonId", lessonId)
            startActivity(intent)
        }
    }
}
