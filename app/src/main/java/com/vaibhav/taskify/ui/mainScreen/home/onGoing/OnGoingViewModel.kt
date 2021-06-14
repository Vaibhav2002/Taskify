package com.vaibhav.taskify.ui.mainScreen.home.onGoing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class OnGoingViewModel @Inject constructor(private val taskRepo: TaskRepo) : ViewModel() {

    val pausedTasks =
        taskRepo.getAllPausedTasks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    val runningTask = taskRepo.getRunningTask()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


}