package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class HomeActivity : AppCompatActivity() {
    lateinit var progressSharedPref: SharedPreferences
    lateinit var sharedProgTotalPreferences: SharedPreferences
    lateinit var sharedTotalPreferences: SharedPreferences
    lateinit var sharedLessonPreferences: SharedPreferences

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)
        DeviceNavigationClass.hideNavigationBar(this)

       var sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
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

        getSubject()
    }

    fun getSubject() {
        progressSharedPref = getSharedPreferences("progressPref", Context.MODE_PRIVATE)
        val editor = progressSharedPref.edit()

        sharedProgTotalPreferences = getSharedPreferences("progressPrefTotal", Context.MODE_PRIVATE)
        val editor2 = sharedProgTotalPreferences.edit()

        sharedTotalPreferences = getSharedPreferences("genericProgTotalPref", Context.MODE_PRIVATE)
        val editor4 = sharedTotalPreferences.edit()


        val subjectIdArray = ArrayList<String>()
        val subjectTranslationArray = ArrayList<String>()
        val subjectArray = ArrayList<String>()
        var subjectProgressArray = ArrayList<String>()

        val ipAddress = ConnectionClass()
        val fileAccess = "getSubject.php"
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
                        var totalSubject = 0

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val subjectId = jsonObject.getString("subjectId")
                            val subject = jsonObject.getString("subject")
                            val subjectCapitalized = subject.substring(0, 1).uppercase() + subject.substring(1).lowercase()
                            val subjectTranslation = "T" + subject

                            Log.d("Current subjects", "")
                            Log.d("HomeActivity", subject)

                            subjectIdArray.add(subjectId)
                            subjectTranslationArray.add(subjectTranslation)
                            subjectArray.add(subjectCapitalized)
                            subjectProgressArray.add("0")
                        }

                        runOnUiThread {
                            createSubjectLayouts(subjectIdArray, subjectArray, subjectTranslationArray, subjectProgressArray)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun createSubjectLayouts(subjectIdArray: ArrayList<String>, subjectArray: ArrayList<String>, subjectTranslationArray: ArrayList<String>, progressArray: ArrayList<String>) {
        runOnUiThread {
            val parentLayout: LinearLayout = findViewById(R.id.parent_layout)

            val inflater = LayoutInflater.from(this)

            for (subject in subjectArray.indices) {
                val subjectLayout = inflater.inflate(R.layout.subject_layout, parentLayout, false) as RelativeLayout

                val imgSubject = subjectLayout.findViewById<ImageView>(R.id.imgSubject)
                val txtSubject = subjectLayout.findViewById<TextView>(R.id.txtSubject)
                val txtTranslation = subjectLayout.findViewById<TextView>(R.id.txtTranslation)
                val progressText = subjectLayout.findViewById<TextView>(R.id.txtProgress)
                val progressBar = subjectLayout.findViewById<ProgressBar>(R.id.progressBar)
                val progressString = progressArray[subject]
                val progress = progressString.replace("%", "").toDouble()
                imgSubject.setImageResource(R.drawable.ic_launcher_background)
                txtSubject.text = subjectArray[subject]
                txtTranslation.text = subjectTranslationArray[subject]
                progressText.text = "$progress%"
                progressBar.progress = progress.toInt()

                val uniqueId = View.generateViewId()

                val subjectId = subjectIdArray[subject]

                subjectLayout.id = uniqueId
                parentLayout.addView(subjectLayout)

                runOnUiThread {
                    subjectLayout.setOnClickListener {
                        Toast.makeText(applicationContext, "Clicked: $uniqueId", Toast.LENGTH_SHORT).show()
                    }
                    when (subject) {
                        0 -> {
                            val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                            layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.margin_start)
                            subjectLayout.layoutParams = layoutParams

                            subjectLayout.setOnClickListener {
                                val intent = Intent(applicationContext, LessonActivity::class.java)
                                intent.putExtra("subjectId", subjectId)
                                println("$subjectId")
                                intent.putExtra("subject", subjectId)
                                startActivity(intent)
                            }
                        }

                        subject -> {
                            val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                            subjectLayout.layoutParams = layoutParams

                            subjectLayout.setOnClickListener {
                                val intent = Intent(applicationContext, LessonActivity::class.java)
                                intent.putExtra("subjectId", subjectId)
                                intent.putExtra("subject", txtSubject.text)
                                startActivity(intent)
                            }
                        }
                        subjectArray.lastIndex -> {
                            val layoutParams = subjectLayout.layoutParams as LinearLayout.LayoutParams
                            layoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                            subjectLayout.layoutParams = layoutParams

                            subjectLayout.setOnClickListener {
                                val intent = Intent(applicationContext, LessonActivity::class.java)
                                intent.putExtra("subjectId", subjectId)
                                intent.putExtra("subject", txtSubject.text)
                                startActivity(intent)
                            }
                        }
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
