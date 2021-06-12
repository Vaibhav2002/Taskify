package com.vaibhav.taskify.ui.mainScreen

import androidx.lifecycle.ViewModel
import com.vaibhav.taskify.data.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    val user = authRepo.getUserData()

    init {
        Timber.d(authRepo.getUserData().toString())
        Timber.d(getUserData().toString())
    }

    fun getUserData() = authRepo.getUserData()

}