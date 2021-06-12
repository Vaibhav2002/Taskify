package com.vaibhav.taskify.data.remote.harperDb


import com.vaibhav.taskify.data.models.requests.SQLModel
import com.vaibhav.taskify.data.models.remote.TaskDTO
import com.vaibhav.taskify.data.models.remote.UserDTO
import com.vaibhav.taskify.util.BASIC_AUTH
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("/")
    suspend fun saveUserInDb(
        @Body body: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH,
    ): Response<Any>

    @POST("/")
    suspend fun getUserInfo(
        @Body sqlModel: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH
    ): Response<List<UserDTO>>

    //tasks
    @POST("/")
    suspend fun insertTask(
        @Body task: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH
    ): Response<Any>

    @POST("/")
    suspend fun getAllTasksOfUser(
        @Body task: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH
    ): Response<List<TaskDTO>>

    @POST("/")
    suspend fun updateTask(
        @Body task: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH
    ): Response<Any>

    @POST("/")
    suspend fun deleteTask(
        @Body task: SQLModel,
        @Header("Authorization") header: String = BASIC_AUTH
    ): Response<Any>
}