package com.vaibhav.taskify.data.remote.harperDb

data class TaskDTO(
    val email: String,
    val task_title: String,
    val task_description: String,
    val task_category: String,
    val start_time: Long,
    val end_time: String,
    val started: Boolean,
    val completed: Boolean,
    val created_time: Long,
    val task_id: String = "$email.$created_time"
)
