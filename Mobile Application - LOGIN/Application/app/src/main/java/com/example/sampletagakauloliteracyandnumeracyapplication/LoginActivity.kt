package com.example.sampletagakauloliteracyandnumeracyapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    @SuppressLint("InlinedApi")

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.txtEmail)
        etPassword = findViewById(R.id.txtPass)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(username, password)
            }
        }
    }

    private fun performLogin(username: String, password: String) {
        ApiService.login(username, password, object : ApiService.ApiCallback {
            override fun onSuccess(response: JSONObject) {
                // Login success
                try {
                    val username = response.getString("username")
                    val lowercaseUsername = username?.let { it.toLowerCase() }
                    val capitalizedUsername = lowercaseUsername?.replaceFirstChar { it.uppercase() }
                    // Handle the successful login
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Logged in as ${capitalizedUsername}", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("username", capitalizedUsername)
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Invalid response from server", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onError(errorMessage: String) {
                // Login failed
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // Define the ApiCallback interface
    interface ApiCallback {
        fun onSuccess(response: JSONObject)
        fun onError(errorMessage: String)
    }
}
