package com.vaibhav.taskify.data.remote.harperDb

import com.vaibhav.taskify.data.models.requests.SQLModel
import com.vaibhav.taskify.util.Resource
import javax.inject.Inject

class HarperDbTaskDataSource @Inject constructor(private val api: Api) {


    private fun getSQLModelForInsertTask(task: Task) =
        SQLModel(
            sql = String.format(
                "INSERT INTO edufy.tasks(task_id, email, task_title, task_description,task_category," +
                        " start_time, end_time, started, completed, created_time) VALUE ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)",
                task.task_id,
                task.email,
                task.task_title,
                task.task_description,
                task.task_category,
                task.start_time,
                task.end_time,
                task.started,
                task.completed,
                task.created_time
            )

        )

    private fun getSQLModelForUpdateTask(task: Task) =
        SQLModel(
            sql = String.format(
                "UPDATE edufy.tasks SET task_title = '%s', task_description = '%s',task_category = '%s'," +
                        "start_time = '%s', end_time = '%s', started = '%s', completed = %s WHERE task_id = '%s'",
                task.task_title,
                task.task_description,
                task.task_category,
                task.start_time,
                task.end_time,
                task.started,
                task.completed,
                task.task_id
            )

        )


    private fun getSqlModelToGetTasks(email: String) = SQLModel(
        sql = String.format(
            "SELECT * FROM edufy.tasks WHERE email = '%s' ORDER BY created_time DESC, start_time DESC",
            email,
        )
    )


    suspend fun insertTask(task: Task): Resource<Task> = try {
        val sqlModel = getSQLModelForInsertTask(task)
        val response = api.insertTask(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = task, response.message())
        else
            Resource.Error("Failed to save task")
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    suspend fun updateTask(task: Task): Resource<Task> = try {
        val sqlModel = getSQLModelForUpdateTask(task)
        val response = api.updateTask(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = task, response.message())
        else
            Resource.Error("Failed to update task")
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }


    suspend fun getAllTasksOfUser(email: String): Resource<List<Task>> = try {
        val sqlModel = getSqlModelToGetTasks(email)
        val response = api.getAllTasksOfUser(sqlModel)
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(data = it)
            } ?: Resource.Error("failed to load tasks")
        } else
            Resource.Error("failed to load tasks")
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }
}
