package com.vaibhav.taskify.data.remote.harperDb

import com.vaibhav.taskify.data.models.requests.SQLModel
import com.vaibhav.taskify.util.Resource
import javax.inject.Inject

class HarperDbAuthDataSource @Inject constructor(private val api: Api) {

    private fun getSQLModelForGettingUserByEmail(email: String): SQLModel {
        return SQLModel("SELECT * FROM edufy.user WHERE email = '${email}' LIMIT 1")
    }

    private fun getSQLModelForStoringUser(userDTO: UserDTO): SQLModel {
        return SQLModel("INSERT INTO edufy.user (username,email, profile_img) VALUE ('${userDTO.username}','${userDTO.email}','${userDTO.profile_img}')")
    }

    suspend fun storeUserIntoDb(userDTO: UserDTO): Resource<UserDTO> = try {
        val sqlModel = getSQLModelForStoringUser(userDTO)
        val response = api.saveUserInDb(sqlModel)
        if (response.isSuccessful)
            Resource.Success(data = userDTO)
        else
            Resource.Error(message = "Failed to store users")
    } catch (e: Exception) {
        Resource.Error(message = e.message.toString())
    }


    suspend fun getUserData(email: String): Resource<UserDTO> = try {
        val sqlModel = getSQLModelForGettingUserByEmail(email)
        val response = api.getUserInfo(sqlModel)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.isNotEmpty())
                    Resource.Success(data = it[0])
                else
                    Resource.Error(message = "User does not exist")
            } ?: Resource.Error(message = "Failed to store user")
        } else Resource.Error(message = "Failed to store users")
    } catch (e: Exception) {
        Resource.Error(message = e.message.toString())
    }

}