package com.vaibhav.taskify.data.models.remote

data class SQLModel(
    val sql: String,
    val operation: String = "sql"
)
