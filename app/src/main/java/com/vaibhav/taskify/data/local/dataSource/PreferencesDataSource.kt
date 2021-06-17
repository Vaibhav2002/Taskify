package com.vaibhav.taskify.data.local.dataSource

import com.vaibhav.taskify.data.models.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {

    fun getUserData(): UserEntity?

    fun getUserDataFlow(): Flow<UserEntity>

    suspend fun saveUserData(userEntity: UserEntity)

    suspend fun removeUserData()

    fun isServiceRunning(): Boolean

    fun isServiceRunningByFlow(): Flow<Boolean>

    suspend fun setServiceRunning(running: Boolean)


}