package com.vaibhav.taskify.data.local.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.vaibhav.taskify.data.models.entity.UserEntity
import javax.inject.Inject

class DataStorePreferencesDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesDataSource {

    companion object {
        val USER_SAVE_KEY = stringPreferencesKey("user")
    }

    /**
     * To be implemented
     */
    override fun getUserData() = null

    override suspend fun saveUserData(userEntity: UserEntity) {
        val serializedUser = Gson().toJson(userEntity)
        dataStore.edit {
            it[USER_SAVE_KEY] = serializedUser
        }
    }

    override suspend fun removeUserData() {
        dataStore.edit {
            it.remove(USER_SAVE_KEY)
        }
    }
}