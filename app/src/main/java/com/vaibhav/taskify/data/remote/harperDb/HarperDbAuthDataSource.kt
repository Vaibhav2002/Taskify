package com.vaibhav.taskify.data.remote.harperDb

import com.vaibhav.taskify.data.models.requests.SQLModel
import com.vaibhav.taskify.util.Resource
import java.util.*
import javax.inject.Inject

class HarperDbAuthDataSource @Inject constructor(private val api: Api) {

    private fun getSQLModelForGettingUserByEmail(email: String): SQLModel {
        return SQLModel("SELECT * FROM edufy.user WHERE email = '${email}' LIMIT 1")
    }

    private fun getSQLModelForStoringUser(user: User): SQLModel {
        return SQLModel("INSERT INTO edufy.user (username,email, profile_img) VALUE ('${user.username}','${user.email}','${user.profile_img}')")
    }

    suspend fun storeUserIntoDb(user: User): Resource<User> = try {
        val sqlModel = getSQLModelForStoringUser(user)
        val response = api.saveUserInDb(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = user)
        else
            Resource.Error(message = "Failed to store users")
    } catch (e: Exception) {
        Resource.Error(message = e.message.toString())
    }


    suspend fun getUserData(email: String): Resource<User> = try {
        val sqlModel = getSQLModelForGettingUserByEmail(email)
        val response = api.getUserInfo(sqlModel)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.isNotEmpty())
                    Resource.Success(data = it[0])
                else
                    Resource.Error(message = "User does not exist")
            } ?: Resource.Error(message = "Failed")
        } else Resource.Error(message = "Failed to store users")
    } catch (e: Exception) {
        Resource.Error(message = e.message.toString())
    }

}