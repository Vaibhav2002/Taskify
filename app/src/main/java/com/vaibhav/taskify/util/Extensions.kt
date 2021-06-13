package com.vaibhav.taskify.util

import android.content.Context
import android.widget.Button
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import timber.log.Timber

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

val Context.dataStore by preferencesDataStore("TaskifyDataStore")

fun Button.setUsable(currentFragment: Int, topLevelScreens: TopLevelScreens) {
    Timber.d("$currentFragment ${topLevelScreens.fragmentId}")
    Timber.d((currentFragment != topLevelScreens.fragmentId).toString())
    isEnabled = currentFragment != topLevelScreens.fragmentId
    alpha = if (currentFragment != topLevelScreens.fragmentId) 1F else 0.5F
}