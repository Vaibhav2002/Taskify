package com.vaibhav.taskify.data.models

import androidx.room.ColumnInfo
import com.vaibhav.taskify.util.TaskType

data class TaskCount(
    @ColumnInfo(name = "task_category")
    val taskType: TaskType,
    val count: Int
)
