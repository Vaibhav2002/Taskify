package com.vaibhav.taskify.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vaibhav.taskify.data.models.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
@TypeConverters(TaskCategoryTypeConverter::class, TaskStateTypeConverter::class)
abstract class TaskifyDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO
}