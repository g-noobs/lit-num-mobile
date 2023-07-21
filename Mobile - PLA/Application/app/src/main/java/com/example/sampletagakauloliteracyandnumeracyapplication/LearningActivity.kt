package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class LearningActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressSharedPref: SharedPreferences
    private lateinit var mediaSharedPref: SharedPreferences

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_learning)

        DeviceNavigationClass.hideNavigationBar(this)

        val lessonId = intent.getStringExtra("lessonId")!!

        getQuiz(lessonId)
        retrieveDataFromPHP(lessonId)
    }

    fun retrieveDataFromPHP(lessonId: String) {
        val subTopicSharedPref = getSharedPreferences("subTopicSharedPref", Context.MODE_PRIVATE)
        val subTopicEditor = subTopicSharedPref.edit()

        sharedPreferences = getSharedPreferences("progressSubTopicPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        progressSharedPref = getSharedPreferences("progressPref",  Context.MODE_PRIVATE)
        val editor3 = progressSharedPref.edit()

        mediaSharedPref = getSharedPreferences("mediaSharedPref", Context.MODE_PRIVATE)
        val editor4 = mediaSharedPref.edit()

        val subTopicIdArray = ArrayList<String>()
        val subTopicContentArray = ArrayList<String>()
        val subTopicTranslationArray = ArrayList<String>()
        val subTopicArray = ArrayList<String>()
        val subTopicProgressArray = ArrayList<String>()

        var imgOnly = true
        var topicTitle = ""
        var capitalizeTopicTitle = ""
        var subTopicTranslation = ""
        var subTopicContent = ""
        var contentImgPath = ""
        var audioId = ""
        var videoId = ""



        val ipAddress = ConnectionClass()
        val fileAccess = "getSubTopicContent.php?param1=$lessonId"
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
                            Toast.makeText(
                                applicationContext,
                                "Failed to retrieve data.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val jsonArray = JSONArray(responseBody)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)

                            imgOnly = jsonObject.getBoolean("imgOnly")

                            val subTopicId = jsonObject.getString("subTopicId")
                            topicTitle = jsonObject.getString("topicTitle")
                            capitalizeTopicTitle =
                                topicTitle.substring(0, 1).uppercase() + topicTitle.substring(1).lowercase()
                            subTopicTranslation = "T" + capitalizeTopicTitle
                            subTopicContent = jsonObject.getString("subtopicContent")
                            contentImgPath = jsonObject.getString("contentImgPath")
                            audioId = jsonObject.getString("audioId")
                            videoId = jsonObject.getString("videoId")

                            editor4.putString(subTopicId, videoId).apply()
                            editor4.putString(subTopicId, audioId).apply()
                            editor4.putString(subTopicId, contentImgPath).apply()
                            editor4.putBoolean("imgOnly", imgOnly).apply()

                            Log.d("This", contentImgPath)
                            subTopicIdArray.add(subTopicId)
                            subTopicContentArray.add(subTopicContent)
                            subTopicTranslationArray.add(subTopicTranslation)
                            subTopicArray.add(capitalizeTopicTitle)
                        }

                        runOnUiThread {
                            var totalNum = 0
                            var num = 0

                            subTopicArray.forEach { subtopic ->
                                Log.d("LearningActivity", subtopic)
                                sharedPreferences.edit()
                                totalNum++

                                val percentage = progressSharedPref.getInt(subtopic, 0)
                                Log.d("LearningActivityPref:", percentage.toString())

                                if (percentage == 0) {
                                    val currentProgress = progressSharedPref.getInt(subtopic, 0)
                                    editor3.putInt(lessonId, currentProgress)
                                    editor3.apply()

                                    subTopicProgressArray += currentProgress.toString()
                                    Log.d("LearningActivityPref:", currentProgress.toString())
                                } else {
                                    val currentProgress = progressSharedPref.getInt(subtopic, 0)
                                    editor3.putInt(lessonId, currentProgress + 1)
                                    editor3.apply()

                                    subTopicProgressArray += currentProgress.toString()
                                    Log.d("LearningActivityPref:", currentProgress.toString())

                                }
                            }

                            editor3.putInt(lessonId, totalNum)
                            editor3.apply()

                            Log.d("LearningActivityTPref", lessonId)
                            Log.d("LearningActivityTPref", num.toString())
                            Log.d("LearningActivityTPref", totalNum.toString())

                            if (imgOnly) {
                                val topicTitleString = topicTitle
                                val topicTitleLength = topicTitleString.length
                                println("The length of the subtopic is: " + topicTitleLength)

                                if (topicTitleLength <= 2) {
                                    getSubTopicButtons(subTopicArray, subTopicIdArray, subTopicTranslationArray, subTopicProgressArray, lessonId)
                                } else {
                                    getSubTopicCards(subTopicArray, subTopicIdArray, subTopicTranslationArray, subTopicProgressArray, lessonId)
                                }
                            } else {
                                getSubTopicStory(subTopicArray, subTopicIdArray, subTopicContentArray, subTopicProgressArray, lessonId)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun getSubTopicButtons(subTopicArray: ArrayList<String>, subTopicIdArray: ArrayList<String>, subTopicTranslationArray: ArrayList<String>, subTopicProgressArray: ArrayList<String>, lessonId: String) {
        runOnUiThread {
            sharedPreferences = getSharedPreferences("progressSubTopicPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val gridLayout: GridLayout = findViewById(R.id.parent_layout)
            val inflater = LayoutInflater.from(this)

            val columnCount = 6

            for (subTopic in subTopicArray.indices) {
                val subTopicId = subTopicIdArray[subTopic]
                val itemLayout = inflater.inflate(R.layout.lesson_alphabet_numbers_layout, gridLayout, false) as LinearLayout
                val textView = itemLayout.findViewById<TextView>(R.id.textView3)
                textView.text = subTopicArray[subTopic]

                val params = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(subTopic % columnCount) // Calculate column index
                    rowSpec = GridLayout.spec(subTopic / columnCount)    // Calculate row index
                    setMargins(0, 0, 62, 36)
                    width = GridLayout.LayoutParams.WRAP_CONTENT
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                }
                gridLayout.addView(itemLayout, params)

                itemLayout.setOnClickListener {
                    val intent = Intent(this@LearningActivity, ImageLearningActivity::class.java)

                    intent.putExtra("subTopicId", subTopicId)
                    intent.putExtra("wordId", subTopicIdArray[subTopic])
                    intent.putExtra("word", subTopicArray[subTopic])
                    intent.putExtra("lessonId", lessonId)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getSubTopicCards(subTopicArray: ArrayList<String>, subTopicIdArray: ArrayList<String>, subTopicTranslationArray: ArrayList<String>, subTopicProgressArray: ArrayList<String>, lessonId: String) {
        runOnUiThread {
            val parentLayout: GridLayout = findViewById(R.id.parent_layout)
            val inflater = LayoutInflater.from(this)

            for (subTopic in subTopicArray.indices) {
                val lessonLayout = inflater.inflate(R.layout.subject_layout, parentLayout, false) as RelativeLayout

                val imgLesson = lessonLayout.findViewById<ImageView>(R.id.imgSubject)

                val lessonId = subTopicIdArray[subTopic]

                val txtLesson = lessonLayout.findViewById<TextView>(R.id.txtSubject)
                val txtTranslation = lessonLayout.findViewById<TextView>(R.id.txtTranslation)
                val progressText = lessonLayout.findViewById<TextView>(R.id.txtProgress)
                val progressBar = lessonLayout.findViewById<ProgressBar>(R.id.progressBar)
                val progressString = subTopicProgressArray[subTopic].toString()
                val progress = progressString.replace("%", "").toInt()
                imgLesson.setImageResource(R.drawable.ic_launcher_background)
                txtLesson.text = subTopicArray[subTopic]
                txtTranslation.text = subTopicTranslationArray[subTopic]
                progressText.text = "$progress%"
                progressBar.progress = progress

                val uniqueId = View.generateViewId()

                lessonLayout.id = uniqueId
                parentLayout.addView(lessonLayout)

                lessonLayout.setOnClickListener {
                    Toast.makeText(this, "Clicked: $uniqueId", Toast.LENGTH_SHORT).show()
                }
                when (subTopic) {
                    0 -> {
                        val layoutParams = lessonLayout.layoutParams as GridLayout.LayoutParams
                        layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.margin_start)
                        lessonLayout.layoutParams = layoutParams

                        lessonLayout.setOnClickListener {
                            val intent = Intent(this, LearningActivity::class.java)
                            var getImageId = mediaSharedPref.getString("imgId", "1")
                            var getAudioId = mediaSharedPref.getString("audioId", "1")

                            intent.putExtra("imgId", getImageId)
                            intent.putExtra("audioId", getAudioId)
                            intent.putExtra("lesson", txtLesson.text)
                            intent.putExtra("lessonId", lessonId)
                            startActivity(intent)
                        }
                    }

                    subTopic -> {
                        val layoutParams = lessonLayout.layoutParams as GridLayout.LayoutParams
                        lessonLayout.layoutParams = layoutParams

                        lessonLayout.setOnClickListener {
                            val intent = Intent(this, LearningActivity::class.java)
                            intent.putExtra("lesson", txtLesson.text)
                            intent.putExtra("lessonId", lessonId)
                            startActivity(intent)
                        }
                    }
                    subTopicArray.lastIndex -> {
                        val layoutParams = lessonLayout.layoutParams as GridLayout.LayoutParams
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

    private fun getSubTopicStory(subTopicArray: ArrayList<String>, subTopicIdArray: ArrayList<String>, subTopicContentArray: ArrayList<String>, subTopicProgressArray: ArrayList<String>, lessonId: String) {
        runOnUiThread {
            val parentLayout: GridLayout = findViewById(R.id.parent_layout)
            val inflater = LayoutInflater.from(this)

            for (subTopic in subTopicArray.indices) {
                val lessonLayout = inflater.inflate(R.layout.lesson_videos_layout, parentLayout, false) as RelativeLayout

                val subTopicId = subTopicIdArray[subTopic]

                val imgStory = lessonLayout.findViewById<ImageView>(R.id.imgVideo)
                val txtSubTopicTitle = lessonLayout.findViewById<TextView>(R.id.textView1)
                val txtContent = lessonLayout.findViewById<TextView>(R.id.textView3)

                var image = subTopicIdArray[subTopic]
                val ipAddress = ConnectionClass()
                val mediaAccess = "Media/$image.jpg"
                val dir = ipAddress + mediaAccess

                Glide.with(this)
                    .load(dir)
                    .into(imgStory)

                txtSubTopicTitle.text = subTopicArray[subTopic]
                txtContent.text = subTopicContentArray[subTopic]

                val uniqueId = View.generateViewId()

                lessonLayout.id = uniqueId
                parentLayout.addView(lessonLayout)

                lessonLayout.setOnClickListener {
                    val intent = Intent(this, VideoLearningActivity::class.java)
                    intent.putExtra("vidId", subTopicId)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getQuiz(lessonId: String) {
        val host = ConnectionClass()
        val fileAccess = "getQuizScore.php?param1=$lessonId"
        val url = host + fileAccess

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to fetch data from server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val quiz = findViewById<Button>(R.id.btnQuiz)

                if (responseBody?.startsWith("<br") == true) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Failed to retrieve data.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val jsonObject = JSONObject(responseBody)
                    val takeQuiz = jsonObject.getString("takeQuiz").toBoolean()
                    runOnUiThread {
                        if (takeQuiz) {
                            println("No need to take quiz")
                            quiz.isEnabled = false
                        }

                        else {
                            println("Needs to take quiz")

                            quiz.isEnabled = true
                            quiz.setOnClickListener {
                                val intent = Intent(this@LearningActivity, QuizActivity::class.java)
                                intent.putExtra("lessonId", lessonId)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        })
    }
    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}