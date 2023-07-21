package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        DeviceNavigationClass.hideNavigationBar(this)

        etEmail = findViewById(R.id.txtEmail)
        etPassword = findViewById(R.id.txtPass)
        btnLogin = findViewById(R.id.btnLogin)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                verifyUser(email, password)
            }
        }
    }

    private fun verifyUser(email: String, password: String) {
        val ipAddress = ConnectionClass()
        val fileAccess = "login.php?param1=$email&param2=$password"
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
                        val jsonObject = JSONObject(responseBody)

                        val name = jsonObject.getString("name")
                        val userId = jsonObject.getString("userId")

                        runOnUiThread {
                            val context = applicationContext
                            Toast.makeText(context, "Logged in as $name", Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, HomeActivity::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("userId", userId)

                            val editor = sharedPreferences.edit()
                            editor.putString("name", name)
                            editor.putString("userId", userId)
                            editor.apply()

                            startActivity(intent)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "No users found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
