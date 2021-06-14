package com.vaibhav.taskify.ui.stopwatchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.util.Resource
import com.vaibhav.taskify.util.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(private val taskRepo: TaskRepo) : ViewModel() {

    private val _startTaskState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val startTaskState: StateFlow<Resource<Unit>> = _startTaskState

    var task: TaskEntity? = null

    fun startTask() = viewModelScope.launch {
        _startTaskState.emit(Resource.Loading())
        task?.let {
            it.state = TaskState.RUNNING
            _startTaskState.emit(taskRepo.updateTask(it))
        } ?: _startTaskState.emit(Resource.Error("Task is null"))
    }

    fun pauseTask(timeLeft: Long) = viewModelScope.launch {
        _startTaskState.emit(Resource.Loading())
        task?.let {
            it.state = TaskState.PAUSED
            it.timeLeft = timeLeft
            _startTaskState.emit(taskRepo.updateTask(it))
        } ?: _startTaskState.emit(Resource.Error("Task is null"))
    }

    fun stopTask(timeLeft: Long) = viewModelScope.launch {
        _startTaskState.emit(Resource.Loading())
        task?.let {
            it.state = TaskState.COMPLETED
            it.timeLeft = timeLeft
            _startTaskState.emit(taskRepo.updateTask(it))
        } ?: _startTaskState.emit(Resource.Error("Task is null"))
    }
}