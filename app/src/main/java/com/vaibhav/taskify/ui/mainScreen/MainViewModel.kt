package com.vaibhav.taskify.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.util.Resource
import com.vaibhav.taskify.util.StopWatchFor
import com.vaibhav.taskify.util.TaskState
import com.vaibhav.taskify.util.TopLevelScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val taskRepo: TaskRepo
) : ViewModel() {

    val user = authRepo.getUserData()

    private val _taskFetchState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val taskFetchState: StateFlow<Resource<Unit>> = _taskFetchState

    private val _currentFragment = MutableStateFlow(TopLevelScreens.HOME)
    val currentFragment: StateFlow<TopLevelScreens> = _currentFragment

    private val _runningTask = taskRepo.getRunningTask()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val runningTask: StateFlow<List<TaskEntity>> = _runningTask

    var isServiceRunning = false

    var stopWatchFor: StopWatchFor? = null
    var task: TaskEntity? = null


    fun getUserData() = authRepo.getUserData()

    fun fetchAllTasks() = viewModelScope.launch {
        Timber.d("fetching all tasks")
        _taskFetchState.emit(Resource.Loading())
        user?.let {
            val resource = taskRepo.fetchAllTasks(it.email)
            _taskFetchState.emit(resource)
        } ?: _taskFetchState.emit(Resource.Error(message = "Failed to get user information"))

    }


    fun setTaskAsCompleted() = viewModelScope.launch {
        Timber.d("Stopping task")
        if (runningTask.value.isNotEmpty()) {
            val task = runningTask.value[0]
            task.state = TaskState.COMPLETED
            task.timeLeft = 0
            taskRepo.updateTask(task)
        }
    }


}