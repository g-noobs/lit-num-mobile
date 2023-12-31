package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class ProfSettingsActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: TextView
    private lateinit var txtGender: TextView
    private lateinit var txtBirthdate: TextView
    private lateinit var txtAddress: EditText
    private lateinit var btnSave: Button

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_prof_settings)

        val icBack = findViewById<ImageView>(R.id.icBack)
        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtGender = findViewById(R.id.txtGender)
        txtBirthdate = findViewById(R.id.txtBirthdate)
        txtAddress = findViewById(R.id.txtAddress)
        btnSave = findViewById(R.id.btnSave)


        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        icBack.setOnClickListener{
            val intent = Intent(this@ProfSettingsActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val userInfoId = intent.getStringExtra("user_id")!!
        RetrieveDataProfSettings(userInfoId)

        btnSave.setOnClickListener {
            val name = txtName.text.toString()
            //val address = txtAddress.text.toString()
            updateDataProfSettings(name, userInfoId)
            val intent = Intent(this@ProfSettingsActivity, HomeActivity::class.java)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.apply()
            startActivity(intent)
        }
    }


    fun RetrieveDataProfSettings (userInfoId: String) {

        val url = "http://192.168.1.2/TTLN/profile.php?param1=$userInfoId" //only change the "192.168.1.2"

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
                        // Handle error condition
                        // For example, display an error message or take appropriate action
                    } else {
                        val jsonObject = JSONObject(responseBody)

                        val name = jsonObject.getString("name")
                        val email = jsonObject.getString("email")
                        val gender = jsonObject.getString("gender")
                        val birthdate = jsonObject.getString("birthdate")

                        val capitalizedGender = gender.substring(0, 1).uppercase() + gender.substring(1).lowercase()

                        runOnUiThread {
                            txtName.setText(name)
                            txtEmail.hint = email
                            txtGender.hint = capitalizedGender
                            txtBirthdate.hint = birthdate
                            txtAddress.setText("address")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun updateDataProfSettings(name: String, userInfoId: String) {
        val url = "http://192.168.1.2/TTLN/setProfile.php?param1=$name&param2=$userInfoId" //only change the "192.168.1.2"

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("name", name)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Request failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Name updated successfully.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}