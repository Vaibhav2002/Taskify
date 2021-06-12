package com.vaibhav.taskify.data.models.requests

data class SQLModel(
    val sql: String,
    val operation: String = "sql"
)
