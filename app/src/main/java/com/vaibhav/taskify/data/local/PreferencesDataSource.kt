package com.vaibhav.taskify.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vaibhav.taskify.data.models.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val USER_SAVE_KEY = "user"
    }


    suspend fun saveUserData(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        val serializedUser = Gson().toJson(userEntity)
        sharedPreferences.edit().putString(USER_SAVE_KEY, serializedUser).apply()
    }

    fun getUserData(): UserEntity? {
        val user = sharedPreferences.getString(USER_SAVE_KEY, null)
        return Gson().fromJson(user, UserEntity::class.java)
    }

    suspend fun removeUserData() = sharedPreferences.edit().remove(USER_SAVE_KEY).apply()


}