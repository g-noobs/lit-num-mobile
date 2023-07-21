package com.example.sampletagakauloliteracyandnumeracyapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ApiService {
    interface ApiCallback {
        fun onSuccess(response: JSONObject)
        fun onError(errorMessage: String)
    }

    companion object {
        private const val BASE_URL = "http://192.168.1.2/TTLN/connection.php"

        fun login(username: String, password: String, callback: ApiCallback) {
            val requestBody = JSONObject().apply {
                try {
                    put("type", "login")
                    put("username", username)
                    put("password", password)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            GlobalScope.launch(Dispatchers.IO) {
                val response = performRequest(BASE_URL, requestBody.toString())
                handleResponse(response, callback)
            }
        }

        private fun performRequest(apiUrl: String, requestBody: String): String? {
            return try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val outputStream = connection.outputStream
                outputStream.write(requestBody.toByteArray())
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    response.toString()
                } else {
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        private fun handleResponse(response: String?, callback: ApiCallback) {
            if (response != null) {
                try {
                    val jsonResponse = JSONObject(response)
                    val error = jsonResponse.getBoolean("error")
                    val message = jsonResponse.getString("message")

                    if (!error) {
                        callback.onSuccess(jsonResponse.getJSONObject("user"))
                    } else {
                        callback.onError(message)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callback.onError("Invalid response from server")
                }
            } else {
                callback.onError("Failed to connect to the server")
            }
        }
    }
}
