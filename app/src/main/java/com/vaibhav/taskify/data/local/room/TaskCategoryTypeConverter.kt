package com.vaibhav.taskify.data.local.room

import androidx.room.TypeConverter
import com.vaibhav.taskify.util.TaskType

class TaskCategoryTypeConverter  {


    @TypeConverter
    fun fromTaskType(taskType: TaskType?):String?{
        return taskType?.name
    }

    @TypeConverter
    fun toTaskType(task:String?):TaskType?{
        return task?.let { TaskType.valueOf(it) }
    }
}