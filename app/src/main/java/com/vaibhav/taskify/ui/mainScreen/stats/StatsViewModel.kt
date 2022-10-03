package com.vaibhav.taskify.ui.mainScreen.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.models.Bar
import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.repo.TaskRepo
import com.vaibhav.taskify.util.DAYS
import com.vaibhav.taskify.util.TaskType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(taskRepo: TaskRepo) : ViewModel() {

    val lastWeekTasks = taskRepo.getAllTasksSinceLastWeek()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _barData = MutableStateFlow<List<Bar>>(emptyList())
    val barData: StateFlow<List<Bar>> = _barData

    private val _donutData = MutableStateFlow<Map<TaskType, List<TaskEntity>>>(emptyMap())
    val donutData: StateFlow<Map<TaskType, List<TaskEntity>>> = _donutData

    private val days = mutableListOf<Calendar>()

    val millisInADay = 24 * 60 * 60 * 1000

    init {
        viewModelScope.launch {
            initializeAllDays()
            listenAndFormatData()
        }
    }

    private suspend fun initializeAllDays() = withContext(Dispatchers.IO) {
        for (i in 1..7)
            days.add(Calendar.getInstance())
        days[0].set(Calendar.HOUR_OF_DAY, 0)
        for (i in 1 until days.size)
            days[i].timeInMillis = days[i - 1].timeInMillis - millisInADay
    }

    private suspend fun initDays(map: MutableMap<Calendar, MutableList<TaskEntity>>) =
        withContext(Dispatchers.IO) {
            val iterator = days.iterator()
            while (iterator.hasNext()) {
                map[iterator.next()] = mutableListOf()
            }
        }

    private suspend fun listenAndFormatData(): Nothing = withContext(Dispatchers.IO) {
        val map = mutableMapOf<Calendar, MutableList<TaskEntity>>()
        initDays(map)
        lastWeekTasks.collect { tasks ->
            tasks.forEach {
                val cal = findCalendarInstanceOfTask(it)
                cal?.let { calendar ->
                    map[calendar]?.add(it)
                }
            }
            map.toSortedMap { cal1, cal2 -> cal1.timeInMillis.compareTo(cal2.timeInMillis) }
            prepareBarData(map)
            prepareDonutData(tasks)
        }
    }

    private suspend fun findCalendarInstanceOfTask(taskEntity: TaskEntity): Calendar? =
        withContext(Dispatchers.IO) {
            var cal: Calendar? = null
            for (calendar in days) {
                if (calendar.timeInMillis < taskEntity.created_time) {
                    cal = calendar
                    break
                }
            }
            return@withContext cal
        }

    private suspend fun prepareBarData(map: Map<Calendar, List<TaskEntity>>) {
        var id = 0
        val barList = mutableListOf<Bar>()
        map.forEach {
            val day = DAYS.getDayFromNumber(it.key[Calendar.DAY_OF_WEEK])
            val bar = Bar(
                barId = id++,
                count = it.value.size,
                day = day.getShortFormFromNumber(),
                dayFull = day.getFullName(),
                timeStamp = it.key.timeInMillis
            )
            barList.add(bar)
        }
        barList.reverse()
        _barData.emit(barList)
    }

    private suspend fun prepareDonutData(data: List<TaskEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d(data.toString())
            val map = data.groupBy {
                it.task_category
            }
            _donutData.emit(map)
        }
}
