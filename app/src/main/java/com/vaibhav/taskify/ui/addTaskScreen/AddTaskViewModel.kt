package com.vaibhav.taskify.ui.addTaskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.util.Resource
import com.vaibhav.taskify.util.TaskType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class AddTaskScreenState(
    val title: String = "",
    val description: String = "",
    val duration: Long = 0,
    val category: TaskType = TaskType.HOME,
    var titleError: String = "",
    val descriptionError: String = "",
    val durationError: String = ""
)

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepo: TaskRepo,
    authRepo: AuthRepo
) : ViewModel() {

    private val user = authRepo.getUserData()

    private val _screenState = MutableStateFlow(AddTaskScreenState())
    val screenState: StateFlow<AddTaskScreenState> = _screenState

    private val _addTaskState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val addTaskState: StateFlow<Resource<Unit>> = _addTaskState

    init {
        Timber.d(user.toString())
    }

    fun onTitleChanged(title: String) = viewModelScope.launch {
        _screenState.emit(_screenState.value.copy(title = title))
    }

    fun onDescriptionChanged(description: String) = viewModelScope.launch {
        _screenState.emit(_screenState.value.copy(description = description))
    }

    fun onDurationChanged(time: Long) = viewModelScope.launch {
        _screenState.emit(_screenState.value.copy(duration = time))
    }

    fun onCategoryChanged(taskType: TaskType) = viewModelScope.launch {
        _screenState.emit(_screenState.value.copy(category = taskType))
    }

    private suspend fun verify(): Boolean {
        var isValid = true
        val screenState = _screenState.value
        if (screenState.title.isEmpty() || screenState.title.isBlank()) {
            _screenState.emit(_screenState.value.copy(titleError = "Please enter a valid title"))
            isValid = false
        }
        if (screenState.description.isEmpty() || screenState.description.isBlank()) {
            _screenState.emit(_screenState.value.copy(descriptionError = "Please enter a valid description"))
            isValid = false
        }
        if (screenState.duration == 0L) {
            _screenState.emit(_screenState.value.copy(durationError = "Please enter a valid duration"))
            isValid = false
        }
        return isValid
    }

    fun onSaveClicked() = viewModelScope.launch {
        if (verify()) {
            _addTaskState.emit(Resource.Loading())
            val task = getTaskEntity()
            val value = taskRepo.addNewTask(task)
            _addTaskState.emit(value)
        } else
            _addTaskState.emit(Resource.Error("Enter all fields correctly"))
    }

    private fun getTaskEntity() = TaskEntity(
        email = user!!.email,
        task_title = screenState.value.title,
        task_description = screenState.value.description,
        task_category = screenState.value.category,
        duration = screenState.value.duration
    )
}
