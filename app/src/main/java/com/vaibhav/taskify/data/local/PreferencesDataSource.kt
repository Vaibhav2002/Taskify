package com.vaibhav.taskify.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.vaibhav.taskify.data.models.entity.UserEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val USER_SAVE_KEY = "user"
    }


    suspend fun saveUserData(userEntity: UserEntity) {
        val serializedUser = Gson().toJson(userEntity)
        sharedPreferences.edit().putString(USER_SAVE_KEY, serializedUser).apply()
    }

    fun getUserData(): UserEntity {
        val user = sharedPreferences.getString(USER_SAVE_KEY, null)
        return Gson().fromJson(user, UserEntity::class.java)
    }

    suspend fun removeUserData() = sharedPreferences.edit().remove(USER_SAVE_KEY).apply()


}