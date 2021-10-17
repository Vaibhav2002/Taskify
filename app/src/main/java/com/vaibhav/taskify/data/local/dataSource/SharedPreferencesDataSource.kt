package com.vaibhav.taskify.data.local.dataSource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vaibhav.taskify.data.models.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesDataSource {

    companion object {
        const val USER_SAVE_KEY = "user"
        const val SERVICE_KEY = "Service"
        const val ON_BOARDING_KEY = "OnBoarding"
    }

    override suspend fun saveUserData(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        val serializedUser = Gson().toJson(userEntity)
        sharedPreferences.edit().putString(USER_SAVE_KEY, serializedUser).apply()
    }

    override fun getUserData(): UserEntity? {
        val user = sharedPreferences.getString(USER_SAVE_KEY, null)
        return Gson().fromJson(user, UserEntity::class.java)
    }

    override suspend fun removeUserData() = sharedPreferences.edit().remove(USER_SAVE_KEY).apply()

    override fun isServiceRunning() = sharedPreferences.getBoolean(SERVICE_KEY, false)

    override suspend fun setServiceRunning(running: Boolean) {
        sharedPreferences.edit().putBoolean(SERVICE_KEY, running).apply()
    }

    override fun getUserDataFlow(): Flow<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun isServiceRunningByFlow(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun isOnBoardingComplete(): Boolean {
        return sharedPreferences.getBoolean(ON_BOARDING_KEY, false)
    }

    override suspend fun setOnBoardingComplete() {
        sharedPreferences.edit().putBoolean(ON_BOARDING_KEY, true).apply()
    }
}
