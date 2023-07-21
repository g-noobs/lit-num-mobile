package com.example.sampletagakauloliteracyandnumeracyapplication

import com.bumptech.glide.Glide
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class LessonActivity : AppCompatActivity() {
    private lateinit var progressSharedPref: SharedPreferences
    private lateinit var sharedRecentPreferences: SharedPreferences
    private lateinit var sharedHomePreferences: SharedPreferences

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_lesson)
        DeviceNavigationClass.hideNavigationBar(this)

        val getSubjectId = intent.getStringExtra("subjectId")!!
        val getSubject = intent.getStringExtra("subject")!!
        progressSharedPref = getSharedPreferences("progressPref", Context.MODE_PRIVATE)
        sharedRecentPreferences = getSharedPreferences("progressPrefTotal", Context.MODE_PRIVATE)
        sharedHomePreferences = getSharedPreferences("genericProgressPref", Context.MODE_PRIVATE)

        var editor = progressSharedPref.edit()
        editor.putString(getSubject, "")

        getLesson(getSubjectId)
    }

    fun getLesson(getSubjectId: String) {
        var editor = progressSharedPref.edit()
        var editor2 = sharedHomePreferences.edit()

        val lessonIdArray = ArrayList<String>()
        val lessonTranslationArray = ArrayList<String>()
        val lessonArray = ArrayList<String>()
        val lessonProgressArray = ArrayList<String>()

        val ipAddress = ConnectionClass()
        val fileAccess = "getLesson.php?param1=$getSubjectId"
        val url = ipAddress + fileAccess

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                try {
                    if (responseBody?.startsWith("<br") == true) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Failed to retrieve data.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val jsonArray = JSONArray(responseBody)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val lessonId = jsonObject.getString("lessonId")
                            println(lessonId)
                            val lesson = jsonObject.getString("lesson")
                            println(lesson)
                            val lessonCapitalized = lesson.substring(0, 1).uppercase() + lesson.substring(1).lowercase()
                            val lessonTranslation = "T" + lesson

                            Log.d("Current subjects", "")
                            Log.d("LessonActivity", lesson)
                            Log.d("LessonActivityId", lessonId)

                            lessonIdArray.add(lessonId)
                            lessonTranslationArray.add(lessonTranslation)
                            lessonArray.add(lessonCapitalized)
                        }

                        runOnUiThread {
                            createLessonLayout(lessonIdArray, lessonArray, lessonTranslationArray, lessonProgressArray)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun createLessonLayout(lessonIdArray: ArrayList<String>, lessonArray: ArrayList<String>, lessonTranslationArray: ArrayList<String>, lessonProgressArray: ArrayList<String>) {
        val parentLayout: LinearLayout = findViewById(R.id.parent_layout)
        val inflater = LayoutInflater.from(this)

        for (lesson in lessonArray.indices) {
            val lessonLayout = inflater.inflate(R.layout.subject_layout, parentLayout, false) as RelativeLayout

            val imgLesson = lessonLayout.findViewById<ImageView>(R.id.imgSubject)

            val lessonId = lessonIdArray[lesson].toString()

            Glide.with(this)
                .load("http://192.168.1.2:8080/TTLN/Media/$lessonId.jpg")
                .into(imgLesson)

            val txtLesson = lessonLayout.findViewById<TextView>(R.id.txtSubject)
            val txtTranslation = lessonLayout.findViewById<TextView>(R.id.txtTranslation)
            val progressText = lessonLayout.findViewById<TextView>(R.id.txtProgress)
            val progressBar = lessonLayout.findViewById<ProgressBar>(R.id.progressBar)
            imgLesson.setImageResource(R.drawable.ic_launcher_background)
            txtLesson.text = lessonArray[lesson].toString()
            txtTranslation.text = lessonTranslationArray[lesson].toString()
            progressText.text = "0%"

            val uniqueId = View.generateViewId()

            lessonLayout.id = uniqueId
            parentLayout.addView(lessonLayout)

            lessonLayout.setOnClickListener {
                Toast.makeText(this, "Clicked: $uniqueId", Toast.LENGTH_SHORT).show()
            }
            when (lesson) {
                0 -> {
                    val layoutParams = lessonLayout.layoutParams as LinearLayout.LayoutParams
                    layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.margin_start)
                    lessonLayout.layoutParams = layoutParams

                    lessonLayout.setOnClickListener {
                        val intent = Intent(this, LearningActivity::class.java)
                        intent.putExtra("lesson", txtLesson.text)
                        intent.putExtra("lessonId", lessonId)
                        startActivity(intent)
                    }
                }

                lesson -> {
                    val layoutParams = lessonLayout.layoutParams as LinearLayout.LayoutParams
                    lessonLayout.layoutParams = layoutParams

                    lessonLayout.setOnClickListener {
                        val intent = Intent(this, LearningActivity::class.java)
                        intent.putExtra("lesson", txtLesson.text)
                        intent.putExtra("lessonId", lessonId)
                        startActivity(intent)
                    }
                }
                lessonArray.lastIndex -> {
                    val layoutParams = lessonLayout.layoutParams as LinearLayout.LayoutParams
                    layoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                    lessonLayout.layoutParams = layoutParams

                    lessonLayout.setOnClickListener {
                        val intent = Intent(this, LearningActivity::class.java)
                        intent.putExtra("lesson", txtLesson.text)
                        intent.putExtra("lessonId", lessonId)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}