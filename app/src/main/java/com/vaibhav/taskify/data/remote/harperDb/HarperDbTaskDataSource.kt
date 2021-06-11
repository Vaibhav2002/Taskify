package com.vaibhav.taskify.data.remote.harperDb

import com.vaibhav.taskify.data.models.requests.SQLModel
import com.vaibhav.taskify.util.Resource
import javax.inject.Inject

class HarperDbTaskDataSource @Inject constructor(private val api: Api) {


    private fun getSQLModelForInsertTask(taskDTO: TaskDTO) =
        SQLModel(
            sql = String.format(
                "INSERT INTO edufy.tasks(task_id, email, task_title, task_description,task_category," +
                        " start_time, end_time, started, completed, created_time) VALUE ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)",
                taskDTO.task_id,
                taskDTO.email,
                taskDTO.task_title,
                taskDTO.task_description,
                taskDTO.task_category,
                taskDTO.start_time,
                taskDTO.end_time,
                taskDTO.started,
                taskDTO.completed,
                taskDTO.created_time
            )

        )

    private fun getSQLModelForUpdateTask(taskDTO: TaskDTO) =
        SQLModel(
            sql = String.format(
                "UPDATE edufy.tasks SET task_title = '%s', task_description = '%s',task_category = '%s'," +
                        "start_time = '%s', end_time = '%s', started = '%s', completed = %s WHERE task_id = '%s'",
                taskDTO.task_title,
                taskDTO.task_description,
                taskDTO.task_category,
                taskDTO.start_time,
                taskDTO.end_time,
                taskDTO.started,
                taskDTO.completed,
                taskDTO.task_id
            )

        )


    private fun getSqlModelToGetTasks(email: String) = SQLModel(
        sql = String.format(
            "SELECT * FROM edufy.tasks WHERE email = '%s' ORDER BY created_time DESC, start_time DESC",
            email,
        )
    )


    suspend fun insertTask(taskDTO: TaskDTO): Resource<TaskDTO> = try {
        val sqlModel = getSQLModelForInsertTask(taskDTO)
        val response = api.insertTask(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = taskDTO, response.message())
        else
            Resource.Error("Failed to save task")
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    suspend fun updateTask(taskDTO: TaskDTO): Resource<TaskDTO> = try {
        val sqlModel = getSQLModelForUpdateTask(taskDTO)
        val response = api.updateTask(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = taskDTO, response.message())
        else
            Resource.Error("Failed to update task")
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }


    suspend fun getAllTasksOfUser(email: String): Resource<List<TaskDTO>> = try {
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
