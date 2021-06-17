package com.vaibhav.taskify.data.repo

import com.vaibhav.taskify.data.local.dataSource.PreferencesDataSource
import javax.inject.Inject
import javax.inject.Named

class PreferencesRepo @Inject constructor(
    @Named("sharedPref") private val preferencesDataSource: PreferencesDataSource,
    @Named("prefDataStore") private val dataStorePreferencesDataSource: PreferencesDataSource
) {

    fun isServiceRunning() = preferencesDataSource.isServiceRunning()

    suspend fun setServiceRunning(running: Boolean) =
        preferencesDataSource.setServiceRunning(running)

}