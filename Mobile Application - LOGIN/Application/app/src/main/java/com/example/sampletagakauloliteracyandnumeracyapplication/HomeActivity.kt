package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class HomeActivity : AppCompatActivity() {
    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        val profSettings = findViewById<ImageView>(R.id.imgProfPic)
        profSettings.setOnClickListener {
            val intent = Intent(this, ProfSettingsActivity::class.java)
            startActivity(intent)
        }

        val logout = findViewById<ImageView>(R.id.imgLogout)
        logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val learnerName: TextView
        learnerName = findViewById(R.id.txtName)
        val getLearnerName = intent.getStringExtra("username")
        learnerName.text = "$getLearnerName"

        val subjectArray: Array<String> = arrayOf("Alphabets", "Numbers", "Stories", "Games", "Culture & Arts", "Vocabulary") // Replace with your array of subjects
        val translationArray: Array<String> = arrayOf("TAlp", "TNum", "TSto", "TGam", "TCaA", "TVoc") // Replace with your array of translation
        val progressArray: Array<Int> = arrayOf(10, 0, 100, 100, 60, 70) // Replace with your array of progress

        val parentLayout: LinearLayout = findViewById(R.id.parent_layout) // Parent layout to hold the dynamically created layouts

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
            progressText.text = "$progress%" // Set the progress value
            progressBar.progress = progress

            val uniqueId = View.generateViewId()

            subjectLayout.id = uniqueId
            parentLayout.addView(subjectLayout)

            subjectLayout.setOnClickListener {
                // Handle click event for the subject layout
                // You can access the clicked layout using the subjectLayout variable here
                // For example, you can retrieve the ID or perform any specific action
                Toast.makeText(this, "Clicked: ${uniqueId}", Toast.LENGTH_SHORT).show()
            }

            if (subject == 0) {
                val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.margin_start)
                subjectLayout.layoutParams = layoutParams

                subjectLayout.setOnClickListener {
                    val intent = Intent(this, AlphabetActivity::class.java)
                    startActivity(intent)
                }
            }

            else if (subject == 1) {
                subjectLayout.setOnClickListener {
                    val intent = Intent(this, NumberActivity::class.java)
                    startActivity(intent)
                }
            }

            else if (subject == 2) {
                subjectLayout.setOnClickListener {
                    val intent = Intent(this, StoryActivity::class.java)
                    startActivity(intent)
                }
            }

            else if (subject == 3) {
                subjectLayout.setOnClickListener {
                    val intent = Intent(this, GameActivity::class.java)
                    startActivity(intent)
                }
            }

            else if (subject == 4) {
                subjectLayout.setOnClickListener {
                    val intent = Intent(this, CultureNArtsActivity::class.java)
                    startActivity(intent)
                }
            }

            else if (subject == subjectArray.lastIndex) {
                val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                layoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                subjectLayout.layoutParams = layoutParams

                subjectLayout.setOnClickListener {
                    val intent = Intent(this, VocabularyActivity::class.java)
                    startActivity(intent)
                }
            }

            else {

            }
        }

    }
}