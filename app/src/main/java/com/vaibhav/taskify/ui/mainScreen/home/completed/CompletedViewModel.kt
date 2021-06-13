package com.vaibhav.taskify.ui.mainScreen.home.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompletedViewModel @Inject constructor(private val taskRepo: TaskRepo) : ViewModel() {

    val completedTasks =
        taskRepo.getAllCompletedTasksOfToday()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}