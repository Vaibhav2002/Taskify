package com.vaibhav.taskify.data.local.room

import androidx.room.*
import com.vaibhav.taskify.data.models.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task_table WHERE created_time > :time ORDER BY created_time DESC")
    fun getTaskAfterGivenTime(time: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE state = :state AND created_time > :todaysTime ORDER BY created_time DESC")
    fun getAllTasksOfToday(
        state: String,
        todaysTime: Long
    ): Flow<List<TaskEntity>>


    @Insert
    suspend fun insertTask(taskEntity: List<TaskEntity>)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

}