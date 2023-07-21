package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import okhttp3.*

class HomeActivity : AppCompatActivity() {

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val userId = sharedPreferences.getString("userId", "")

        val profSettings = findViewById<ImageView>(R.id.imgProfPic)
        profSettings.setOnClickListener {
            val intent = Intent(this, ProfSettingsActivity::class.java)
            intent.putExtra("user_id",userId)
            startActivity(intent)
        }

        val logout = findViewById<ImageView>(R.id.imgLogout)
        logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val learnerName: TextView = findViewById(R.id.txtName)
        learnerName.text = name


        val subjectArray: Array<String> = arrayOf("Alphabets", "Numbers", "Stories", "Games", "Culture & Arts", "Vocabulary")
        val translationArray: Array<String> = arrayOf("TAlp", "TNum", "TSto", "TGam", "TCaA", "TVoc")
        val progressArray: Array<Int> = arrayOf(10, 0, 100, 100, 60, 70)

        val parentLayout: LinearLayout = findViewById(R.id.parent_layout)

        val inflater = LayoutInflater.from(this)

        for (subject in subjectArray.indices) {
            val subjectLayout = inflater.inflate(R.layout.subject_layout, parentLayout, false) as LinearLayout

            val imgSubject = subjectLayout.findViewById<ImageView>(R.id.imgSubject)
            val txtSubject = subjectLayout.findViewById<TextView>(R.id.txtSubject)
            val txtTranslation = subjectLayout.findViewById<TextView>(R.id.txtTranslation)
            val progressText = subjectLayout.findViewById<TextView>(R.id.txtProgress)
            val progressBar = subjectLayout.findViewById<ProgressBar>(R.id.progressBar)
            val progress = progressArray[subject]
            imgSubject.setImageResource(R.drawable.ic_launcher_background)
            txtSubject.text = subjectArray[subject]
            txtTranslation.text = translationArray[subject]
            progressText.text = "$progress%"
            progressBar.progress = progress

            val uniqueId = View.generateViewId()

            subjectLayout.id = uniqueId
            parentLayout.addView(subjectLayout)

            subjectLayout.setOnClickListener {
                Toast.makeText(this, "Clicked: $uniqueId", Toast.LENGTH_SHORT).show()
            }

            when (subject) {
                0 -> {
                    val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                    layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.margin_start)
                    subjectLayout.layoutParams = layoutParams

                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, AlphabetActivity::class.java)
                        startActivity(intent)
                    }
                }
                1 -> {
                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, NumberActivity::class.java)
                        startActivity(intent)
                    }
                }
                2 -> {
                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, StoryActivity::class.java)
                        startActivity(intent)
                    }
                }
                3 -> {
                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, GameActivity::class.java)
                        startActivity(intent)
                    }
                }
                4 -> {
                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, CultureNArtsActivity::class.java)
                        startActivity(intent)
                    }
                }
                subjectArray.lastIndex -> {
                    val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                    layoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                    subjectLayout.layoutParams = layoutParams

                    subjectLayout.setOnClickListener {
                        val intent = Intent(this, VocabularyActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
