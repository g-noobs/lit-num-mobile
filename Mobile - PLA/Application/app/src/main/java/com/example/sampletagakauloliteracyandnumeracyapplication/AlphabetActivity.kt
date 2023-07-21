package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AlphabetActivity : AppCompatActivity() {
    @SuppressLint("InlinedApi")

    private lateinit var gridLayout: GridLayout
    private val itemsArray: Array<String> = arrayOf("Aa", "Bb", "Dd", "Ee", "Ff", "Gg", "Hh", "Ii", "Kk", "Ll", "Mm", "Nn", "Oo", "Ss", "Tt", "Uu", "Ww", "Yy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_alphabet)

        val back = findViewById<ImageView>(R.id.icBack)
        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val takeQuiz = findViewById<Button>(R.id.btnTakeQuiz)

        val allTaken = 0
        if (allTaken != itemsArray.size) {
            takeQuiz.isEnabled = false
        } else {
            takeQuiz.isEnabled = true
        }
        gridLayout = findViewById(R.id.parent_layout)
        val columnCount = 6 // Number of columns in the grid

        val inflater = LayoutInflater.from(this)

        for (i in itemsArray.indices) {
            val itemLayout = inflater.inflate(R.layout.lesson_alphabet_numbers_layout, gridLayout, false) as LinearLayout
            val textView = itemLayout.findViewById<TextView>(R.id.textView3)
            textView.text = itemsArray[i]

            val params = GridLayout.LayoutParams().apply {
                columnSpec = GridLayout.spec(i % columnCount) // Calculate column index
                rowSpec = GridLayout.spec(i / columnCount)    // Calculate row index
                setMargins(0, 0, 62, 36)
                width = GridLayout.LayoutParams.WRAP_CONTENT                            // Set the width
                height = GridLayout.LayoutParams.WRAP_CONTENT
            }
            gridLayout.addView(itemLayout, params)
        }
    }
}