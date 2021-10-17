package com.vaibhav.taskify.ui.mainScreen.home.upComing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor(taskRepo: TaskRepo) : ViewModel() {

    val upComingTasks =
        taskRepo.getAllUpComingTasksOfToday()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
