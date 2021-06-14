package com.vaibhav.taskify.data.models.remote

data class TaskDTO(
    val email: String,
    val task_title: String,
    val task_description: String,
    val task_category: String,
    val state: String,
    val duration: Long,
    val time_left: Long,
    val created_time: Long,
    val task_id: String = "$email.$created_time"
)
