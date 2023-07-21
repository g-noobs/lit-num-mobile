package com.example.sampletagakauloliteracyandnumeracyapplication

import android.app.Activity
import android.view.View

object DeviceNavigationClass {
    fun hideNavigationBar(activity: Activity) {
        val decorView = activity.window.decorView
        val flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = flags
    }
}
