package com.vaibhav.taskify.ui.mainScreen.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@HiltViewModel
class StatsViewModel @Inject constructor(private val taskRepo: TaskRepo) : ViewModel() {

    val lastWeekTasks = taskRepo.getAllTasksSinceLastWeek()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _dataFormatted = MutableStateFlow<Map<Calendar, List<TaskEntity>>>(emptyMap())
    val dataFormatted: StateFlow<Map<Calendar, List<TaskEntity>>> = _dataFormatted

    private val days = mutableListOf<Calendar>()

    val millisInADay = 24 * 60 * 60 * 1000

    init {
//        initializeAllDays()
//        listenAndFormatData()
    }

    private fun initializeAllDays() = viewModelScope.launch(Dispatchers.IO) {
        for (i in 1..7)
            days.add(Calendar.getInstance())
        for (i in 1 until days.size)
            days[i].timeInMillis = days[i - 1].timeInMillis - millisInADay
    }


    private fun listenAndFormatData() = viewModelScope.launch(Dispatchers.IO) {
        val map = mutableMapOf<Calendar, MutableList<TaskEntity>>()
        lastWeekTasks.collect { tasks ->
            tasks.forEach {
                val cal = findCalendarInstanceOfTask(it)
                cal?.let { calendar ->
                    if (map[calendar] == null)
                        map[calendar] = mutableListOf()
                    map[calendar]?.add(it)
                }
            }
            Timber.d("Map $map")
            map.toSortedMap { cal1, cal2 -> cal1.timeInMillis.compareTo(cal2.timeInMillis) * -1 }
            _dataFormatted.emit(map)
        }
    }

    private fun findCalendarInstanceOfTask(taskEntity: TaskEntity): Calendar? {
        var cal: Calendar? = null
        for (calendar in days) {
            if (calendar.timeInMillis < taskEntity.created_time) {
                cal = calendar
                break
            }
        }
        return cal
    }

}