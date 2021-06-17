package com.vaibhav.taskify.ui.addTaskScreen.taskTimeSelect

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskTimeViewModel @Inject constructor() : ViewModel() {

    var hours = 0
    var minutes = 0
    var seconds = 0

    val hoursList = (0..10).toList()
    val minutesList = (0..60).toList()
    val secondsList = (0..60).toList()


    fun onSubmitPressed(): Long {
        return calculateMillis()
    }

    private fun calculateMillis(): Long {
        var ans = seconds * 1000L
        ans += minutes * 60 * 1000
        ans += hours * 60 * 60 * 1000
        return ans
    }

}