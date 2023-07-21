package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList


class QuizActivity : AppCompatActivity() {
    private lateinit var parentLayout: LinearLayout
    private lateinit var imgItem: ImageView

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_quiz)

        DeviceNavigationClass.hideNavigationBar(this)

        val lessonId = intent.getStringExtra("lessonId")
        getDataFromPHP(lessonId.toString())


        parentLayout = findViewById(R.id.parent_layout)
        imgItem = findViewById(R.id.imgItem)
    }

    private fun getDataFromPHP(lessonId: String) {
        val lessonIdArray = ArrayList<String>()
        val questionArray = ArrayList<String>()
        val imageArray = ArrayList<String>()
        val choiceAArray = ArrayList<String>()
        val choiceBArray = ArrayList<String>()
        val choiceCArray = ArrayList<String>()
        val choiceDArray = ArrayList<String>()

        val host = ConnectionClass()
        val fileAccess = "getQuiz.php?param1=$lessonId"
        val url = host + fileAccess

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
                        val item = jsonArray.getJSONObject(i)

                        val quizImg = item.getString("quizImg")
                        val question = item.getString("quizQuestion")
                        val choiceA = item.getString("choiceA")
                        val choiceB = item.getString("choiceB")
                        val choiceC = item.getString("choiceC")
                        val choiceD = item.getString("choiceD")

                        lessonIdArray.add(lessonId)
                        imageArray.add(quizImg)
                        questionArray.add(question)
                        choiceAArray.add(choiceA)
                        choiceBArray.add(choiceB)
                        choiceCArray.add(choiceC)
                        choiceDArray.add(choiceD)
                    }

                    runOnUiThread {
                            var choiceA = choiceAArray[0].uppercase().trim()
                            var choiceB = choiceBArray[0].uppercase().trim()

                            if (!choiceA.isNullOrEmpty()) {
                                println(choiceA)
                                if (choiceA == "TRUE") {
                                    getTrueOrFalseLayout(lessonIdArray, imageArray, questionArray, choiceAArray)
                                }

                                else if (choiceA == "FALSE") {
                                    getTrueOrFalseLayout(lessonIdArray, imageArray, questionArray, choiceAArray)
                                }

                                else if (choiceB.isNullOrEmpty()) {
                                    getIdentificationLayout(lessonIdArray, imageArray, questionArray, choiceAArray)
                                }

                                else {
                                    getMultipleChoiceLayout(lessonIdArray, imageArray, questionArray, choiceAArray, choiceBArray, choiceCArray, choiceDArray)
                                }

                            }
                            else {
                                getEssayLayout(lessonIdArray, imageArray, questionArray)
                            }

                    }
                }
            }
        })
    }

    private fun getIdentificationLayout(lessonId: ArrayList<String>, imageArray: ArrayList<String>, question: ArrayList<String>, correctAns: ArrayList<String>) {
        runOnUiThread {
            val new = question.indices.toMutableList()
            Collections.shuffle(new)

            val randomIdArray = ArrayList<String>(lessonId.size)
            val randomQArray = ArrayList<String>(question.size)
            val randomAArray = ArrayList<String>(correctAns.size)
            val randomImgArray = ArrayList<String>(imageArray.size)

            for (items in new) {
                randomIdArray.add(lessonId[items])
                randomQArray.add(question[items])
                randomAArray.add(correctAns[items])
                randomImgArray.add(imageArray[items])
            }

            println("New set of quiz id: $randomIdArray")
            println("New set of questions: $randomQArray")
            println("New set of answers: $randomAArray")

            var currentIndex = 0
            var currentNum = 1
            var score = 0
            var currentScore = 0


            val linearLayout: LinearLayout = findViewById(R.id.parent_layout)
            val inflater = LayoutInflater.from(this)
            val quizLayout = inflater.inflate(R.layout.idquiz_layout, linearLayout, false) as LinearLayout

            val lblNumber = quizLayout.findViewById<TextView>(R.id.lblNum)
            val lblQuestion = quizLayout.findViewById<TextView>(R.id.lblQuestion)
            val txtAnswer = quizLayout.findViewById<EditText>(R.id.txtAnswer)

            lblNumber.setText((currentIndex + 1).toString())
            lblQuestion.setText(question[currentIndex])

            var image = imageArray[currentIndex]
            val ipAddress = ConnectionClass()
            val mediaAccess = "Media/$image.jpg"
            val dir = ipAddress + mediaAccess

            Glide.with(this)
                .load(dir)
                .into(imgItem)

            val uniqueId = View.generateViewId()
            quizLayout.id = uniqueId
            linearLayout.addView(quizLayout)

                for (quizIndex in randomIdArray.indices) {
                    val btnSubmit = quizLayout.findViewById<Button>(R.id.btnSubmit)
                    btnSubmit.setOnClickListener {
                        if (txtAnswer.text.toString() == correctAns[currentIndex]) {
                            score = 1
                            currentScore++
                        } else {
                            score = 0
                        }

                        val lessonId = randomIdArray[currentIndex]
                        val quizQuestion = randomQArray[currentIndex]
                        val image = randomImgArray[currentIndex]

                        setScore(lessonId, quizQuestion, score)
                        println("you got a score of $score")

                        currentIndex++
                        currentNum++

                        if (currentIndex >= randomIdArray.size) {
                            val intent = Intent(applicationContext, ScoreActivity::class.java)
                            intent.putExtra("lessonId", lessonId)
                            intent.putExtra("score", currentScore)
                            intent.putExtra("totalscore", currentIndex)
                            startActivity(intent)

                            txtAnswer.text = null
                        } else {
                            txtAnswer.text = null

                            var image = imageArray[currentIndex]
                            val ipAddress = ConnectionClass()
                            val mediaAccess = "Media/$image.jpg"
                            val dir = ipAddress + mediaAccess

                            Glide.with(this)
                                .load(dir)
                                .into(imgItem)

                            lblNumber.text = currentNum.toString()
                            lblQuestion.text = question[currentIndex]
                        }

                    }
                }
        }

    }

    private fun getTrueOrFalseLayout(lessonId: ArrayList<String>, imageArray: ArrayList<String>, question: ArrayList<String>, correctAns: ArrayList<String>) {
        runOnUiThread {
        val new = question.indices.toMutableList()
        Collections.shuffle(new)

        val randomIdArray = ArrayList<String>(lessonId.size)
        val randomQArray = ArrayList<String>(question.size)
        val randomAArray = ArrayList<String>(correctAns.size)

        for (items in new) {
            randomIdArray.add(lessonId[items])
            randomQArray.add(question[items])
            randomAArray.add(correctAns[items])
        }

        println("New set of quiz id: $randomIdArray")
        println("New set of questions: $randomQArray")
        println("New set of answers: $randomAArray")

        var currentIndex = 0
        var currentNum = 1
        var score = 0
        var currentScore = 0


        val linearLayout: LinearLayout = findViewById(R.id.parent_layout)
        val inflater = LayoutInflater.from(this)
        val quizLayout = inflater.inflate(R.layout.tofquiz_layout, linearLayout, false) as LinearLayout

        val lblNumber = quizLayout.findViewById<TextView>(R.id.lblNum)
        val lblQuestion = quizLayout.findViewById<TextView>(R.id.lblQuestion)
        val btnTrue = quizLayout.findViewById<Button>(R.id.btnTrue)
        val btnFalse = quizLayout.findViewById<Button>(R.id.btnFalse)

        lblNumber.text = currentNum.toString()
        lblQuestion.setText(question[currentIndex])

        val uniqueId = View.generateViewId()
        quizLayout.id = uniqueId
        linearLayout.addView(quizLayout)

            btnTrue.setOnClickListener {
                if (btnTrue.text.toString() == correctAns[currentIndex]) {
                    score = 1
                    currentScore++
                } else {
                    score = 0
                }

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    val intent = Intent(applicationContext, ScoreActivity::class.java)
                    intent.putExtra("lessonId", lessonId)
                    intent.putExtra("score", currentScore)
                    intent.putExtra("totalscore", currentIndex)
                    startActivity(intent)
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]
                }
            }

            btnFalse.setOnClickListener {
                if (btnFalse.text.toString() == correctAns[currentIndex]) {
                    score = 1
                    currentScore++
                } else {
                    score = 0
                }

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    val intent = Intent(applicationContext, ScoreActivity::class.java)
                    intent.putExtra("lessonId", lessonId)
                    intent.putExtra("score", currentScore)
                    intent.putExtra("totalscore", currentIndex)
                    startActivity(intent)
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]
                }
            }
        }


    }

    private fun getMultipleChoiceLayout( lessonId: ArrayList<String>, imageArray: ArrayList<String>, question: ArrayList<String>, correctAns: ArrayList<String>, choiceBArray: ArrayList<String>, choiceCArray: ArrayList<String>,  choiceDArray: ArrayList<String>) {
        runOnUiThread {
        val new = question.indices.toMutableList()
        Collections.shuffle(new)

        val randomIdArray = ArrayList<String>(lessonId.size)
        val randomQArray = ArrayList<String>(question.size)
        val randomAArray = ArrayList<String>(correctAns.size)
        val randomBArray = ArrayList<String>(choiceBArray.size)
        val randomCArray = ArrayList<String>(choiceCArray.size)
        val randomDArray = ArrayList<String>(choiceDArray.size)

        for (items in new) {
            randomIdArray.add(lessonId[items])
            randomQArray.add(question[items])
            randomAArray.add(correctAns[items])
            randomBArray.add(choiceBArray[items])
            randomCArray.add(choiceCArray[items])
            randomDArray.add(choiceDArray[items])
        }

        println("New set of quiz id: $randomIdArray")
        println("New set of questions: $randomQArray")
        println("New set of answers: $randomAArray")

        var currentIndex = 0
        var currentNum = 1
        var score = 0
        var currentScore = 0

        val linearLayout: LinearLayout = findViewById(R.id.parent_layout)
        val inflater = LayoutInflater.from(this)
        val quizLayout =
            inflater.inflate(R.layout.mcquiz_layout, linearLayout, false) as LinearLayout

        val lblNumber = quizLayout.findViewById<TextView>(R.id.lblNum)
        val lblQuestion = quizLayout.findViewById<TextView>(R.id.lblQuestion)
        val btnChoiceA = quizLayout.findViewById<Button>(R.id.btnChoiceA)
        val btnChoiceB = quizLayout.findViewById<Button>(R.id.btnChoiceB)
        val btnChoiceC = quizLayout.findViewById<Button>(R.id.btnChoiceC)

        lblNumber.text = currentNum.toString()
        lblQuestion.text = question[currentIndex]

        val uniqueId = View.generateViewId()
        quizLayout.id = uniqueId
        linearLayout.addView(quizLayout)

        val options = mutableListOf<String>()

        options.clear()
        options.add(correctAns[currentIndex])
        options.add(choiceBArray[currentIndex])
        options.add(choiceCArray[currentIndex])
        options.shuffle()

        btnChoiceA.text = options[0]
        btnChoiceB.text = options[1]
        btnChoiceC.text = options[2]

            btnChoiceA.setOnClickListener {
                val correctAnswer = correctAns[currentIndex]

                if (btnChoiceA.text.toString() == correctAnswer) {
                    score = 1
                    currentScore++
                } else {
                    score = 0
                }

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    val intent = Intent(applicationContext, ScoreActivity::class.java)
                    intent.putExtra("lessonId", lessonId)
                    intent.putExtra("score", currentScore)
                    intent.putExtra("totalscore", currentIndex)
                    startActivity(intent)
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]

                    options.clear()
                    options.add(correctAns[currentIndex])
                    options.add(choiceBArray[currentIndex])
                    options.add(choiceCArray[currentIndex])
                    options.shuffle()

                    btnChoiceA.text = options[0]
                    btnChoiceB.text = options[1]
                    btnChoiceC.text = options[2]
                }
            }

            btnChoiceB.setOnClickListener {
                val correctAnswer = correctAns[currentIndex]

                if (btnChoiceB.text.toString() == correctAnswer) {
                    score = 1
                    currentScore++
                } else {
                    score = 0
                }

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    val intent = Intent(applicationContext, ScoreActivity::class.java)
                    intent.putExtra("lessonId", lessonId)
                    intent.putExtra("score", currentScore)
                    intent.putExtra("totalscore", currentIndex)
                    startActivity(intent)
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]

                    options.clear()
                    options.add(correctAns[currentIndex])
                    options.add(choiceBArray[currentIndex])
                    options.add(choiceCArray[currentIndex])
                    options.shuffle()

                    btnChoiceA.text = options[0]
                    btnChoiceB.text = options[1]
                    btnChoiceC.text = options[2]
                }
            }

            btnChoiceC.setOnClickListener {
                val correctAnswer = correctAns[currentIndex]

                if (btnChoiceC.text.toString() == correctAnswer) {
                    score = 1
                    currentScore++
                } else {
                    score = 0
                }

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    val intent = Intent(applicationContext, ScoreActivity::class.java)
                    intent.putExtra("lessonId", lessonId)
                    intent.putExtra("score", currentScore)
                    intent.putExtra("totalscore", currentIndex)
                    startActivity(intent)
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]

                    options.clear()
                    options.add(correctAns[currentIndex])
                    options.add(choiceBArray[currentIndex])
                    options.add(choiceCArray[currentIndex])
                    options.shuffle()

                    btnChoiceA.text = options[0]
                    btnChoiceB.text = options[1]
                    btnChoiceC.text = options[2]
                }
            }
        }
    }

    private fun getEssayLayout(lessonId: ArrayList<String>, imageArray: ArrayList<String>, question: ArrayList<String>) {
        runOnUiThread {
            val new = question.indices.toMutableList()
            Collections.shuffle(new)

            val randomIdArray = ArrayList<String>(lessonId.size)
            val randomQArray = ArrayList<String>(question.size)

            for (items in new) {
                randomIdArray.add(lessonId[items])
                randomQArray.add(question[items])
            }

            println("New set of quiz id: $randomIdArray")
            println("New set of questions: $randomQArray")

            var currentIndex = 0
            var currentNum = 1
            var score = 0

            val linearLayout: LinearLayout = findViewById(R.id.parent_layout)
            val inflater = LayoutInflater.from(this)
            val quizLayout = inflater.inflate(R.layout.essayquiz_layout, linearLayout, false) as LinearLayout

            val lblNumber = quizLayout.findViewById<TextView>(R.id.lblNum)
            val lblQuestion = quizLayout.findViewById<TextView>(R.id.lblQuestion)
            val btnSubmit = quizLayout.findViewById<Button>(R.id.btnSubmit)

            lblNumber.text = currentNum.toString()
            lblQuestion.text = question[currentIndex]

            val uniqueId = View.generateViewId()
            quizLayout.id = uniqueId
            linearLayout.addView(quizLayout)

            btnSubmit.setOnClickListener {
                score = 1

                val lessonId = randomIdArray[currentIndex]
                val quizQuestion = randomQArray[currentIndex]
                setScore(lessonId, quizQuestion, score)
                println("You got a score of $score")

                currentIndex++
                currentNum++

                if (currentIndex >= randomIdArray.size) {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                } else {
                    lblNumber.text = currentNum.toString()
                    lblQuestion.text = question[currentIndex]
                }
            }
        }
    }

    private fun setScore(lessonId: String, quizQuestion: String, score: Int){
        val host = ConnectionClass()
        val fileAccess = "setQuizScore.php?param1=$lessonId&param2=$quizQuestion&param3=$score"
        val url = host + fileAccess

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to change score.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                if (responseBody?.startsWith("<br") == true) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Failed to retrieve data.", Toast.LENGTH_SHORT).show()
                    }
                } else {

                }
            }
        })
    }
}