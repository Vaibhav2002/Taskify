package com.vaibhav.taskify.data.local.dataSource

import com.vaibhav.taskify.data.models.entity.UserEntity

interface PreferencesDataSource {

    fun getUserData(): UserEntity?

    suspend fun saveUserData(userEntity: UserEntity)

    suspend fun removeUserData()
}