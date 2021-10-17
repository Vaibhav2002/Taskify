package com.vaibhav.taskify.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.data.repo.PreferencesRepo
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
    private val taskRepo: TaskRepo,
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {

    val user = authRepo.getUserData()

    private val _taskFetchState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val taskFetchState: StateFlow<Resource<Unit>> = _taskFetchState

    private val _currentFragment = MutableStateFlow(TopLevelScreens.HOME)

    private val _runningTask = taskRepo.getRunningTask()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val runningTask: StateFlow<List<TaskEntity>> = _runningTask

    fun isServiceRunning() = preferencesRepo.isServiceRunning()

    var stopWatchFor: StopWatchFor? = null
    var task: TaskEntity? = null

    private val _logout = MutableStateFlow(false)
    val shouldLogout: StateFlow<Boolean> = _logout

    fun fetchAllTasks() = viewModelScope.launch {
        Timber.d("fetching all tasks")
        _taskFetchState.emit(Resource.Loading())
        user?.let {
            val resource = taskRepo.fetchAllTasks(it.email)
            _taskFetchState.emit(resource)
        } ?: _taskFetchState.emit(Resource.Error(message = "Failed to get user information"))
    }

    fun saveServiceStarted() = saveServiceState(true)

    fun saveServiceStopped() = saveServiceState(false)

    private fun saveServiceState(running: Boolean) = viewModelScope.launch {
        preferencesRepo.setServiceRunning(running)
    }

    fun onLogoutPressed() = viewModelScope.launch {
        authRepo.logoutUser()
        _logout.emit(true)
    }
}
