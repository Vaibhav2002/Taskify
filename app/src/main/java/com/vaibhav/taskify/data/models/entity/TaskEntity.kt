package com.vaibhav.taskify.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vaibhav.taskify.util.TaskType

@Entity(tableName = "task_table")
data class TaskEntity(
    val email: String,
    val task_title: String,
    val task_description: String,
    val task_category: TaskType,
    val start_time: Long,
    val end_time: Long,
    val started: Boolean,
    val completed: Boolean,
    val created_time: Long = System.currentTimeMillis(),
    @PrimaryKey
    val task_id: String = "$email.$created_time"
)
