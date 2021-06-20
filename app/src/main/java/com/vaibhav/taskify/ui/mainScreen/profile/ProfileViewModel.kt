package com.vaibhav.taskify.ui.mainScreen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.ui.mainScreen.profile.ProfileViewModel.ProfileScreenTaskCount.*
import com.vaibhav.taskify.util.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val taskRepo: TaskRepo
) : ViewModel() {

    enum class ProfileScreenTaskCount {
        COMPLETED, INCOMPLETE, TOTAL
    }

    val taskCounts = taskRepo.getTaskCounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val taskStates = flow<Map<ProfileScreenTaskCount, Int>> {
        val map = mutableMapOf<ProfileScreenTaskCount, Int>()
        taskRepo.getTaskStates()
            .map {
                map[TOTAL] = it.size
                it.groupBy { taskState ->
                    taskState
                }
            }.collect {
                map[COMPLETED] = it[TaskState.COMPLETED]?.size ?: 0
                map[INCOMPLETE] =
                    (it[TaskState.NOT_STARTED]?.size ?: 0) + (it[TaskState.PAUSED]?.size ?: 0)
                emit(map)
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())


    private val _logout = MutableStateFlow(false)
    val shouldLogout: StateFlow<Boolean> = _logout

    fun getUserData() = authRepo.getUserData()


    fun onLogoutPressed() = viewModelScope.launch {
        authRepo.logoutUser()
        _logout.emit(true)
    }


}