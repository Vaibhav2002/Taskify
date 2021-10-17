package com.vaibhav.taskify.ui.mainScreen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.util.TaskState
import com.vaibhav.taskify.util.TaskStateCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val taskRepo: TaskRepo
) : ViewModel() {

    val taskCounts = taskRepo.getTaskCounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val taskStates = flow {
        var total = 0
        var completed = 0
        taskRepo.getTaskStates()
            .map {
                total = it.size
                it.groupBy { taskState ->
                    taskState
                }
            }.collect {
                completed = it[TaskState.COMPLETED]?.size ?: 0
                emit(TaskStateCount(completed, total))
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TaskStateCount())

    private val _logout = MutableStateFlow(false)
    val shouldLogout: StateFlow<Boolean> = _logout

    fun getUserData() = authRepo.getUserData()

    fun onLogoutPressed() = viewModelScope.launch {
        authRepo.logoutUser()
        _logout.emit(true)
    }
}
