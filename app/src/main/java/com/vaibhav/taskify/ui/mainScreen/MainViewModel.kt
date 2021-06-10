package com.vaibhav.taskify.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    private val user = authRepo.getUserData()


    init {
        Timber.d(user.toString())
    }
}