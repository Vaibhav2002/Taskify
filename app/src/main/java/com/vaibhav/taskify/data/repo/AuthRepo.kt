package com.vaibhav.taskify.data.repo


import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.vaibhav.taskify.data.local.PreferencesDataSource
import com.vaibhav.taskify.data.models.mappper.UserMapper
import com.vaibhav.taskify.data.remote.FirebaseAuthDataSource
import com.vaibhav.taskify.data.remote.harperDb.HarperDbAuthDataSource
import com.vaibhav.taskify.data.remote.harperDb.UserDTO
import com.vaibhav.taskify.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val harperDbAuthDataSource: HarperDbAuthDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val userMapper: UserMapper
) {

    fun getUserData() = preferencesDataSource.getUserData()

    suspend fun loginUser(email: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val resource = authDataSource.loginUser(email, password)
            return@withContext getUserDataAfterLogin<Unit>(resource, email)
        }

    suspend fun registerUser(email: String, username: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val resource =
                authDataSource.registerUser(email, username = username, password = password)
            val userDTO = UserDTO(email = email, username = username)
            return@withContext storeUserDataAfterRegister(resource, userDTO)
        }


    suspend fun registerUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error)
                return@withContext Resource.Error("Failed to sign in using Google")
            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            val userDTO = resource.data?.let {
                getUserDTOFromFirebaseUser(it)
            } ?: UserDTO()
            return@withContext storeUserDataAfterRegister(resource, userDTO)
        }


    suspend fun loginUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error)
                return@withContext Resource.Error("Failed to sign in using Google")
            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            return@withContext getUserDataAfterLogin(resource, resource.data!!.email!!)
        }


//    suspend fun registerUsingFacebook(token: AccessToken): Resource<Unit> =
//        withContext(Dispatchers.IO) {
//            val credential = FacebookAuthProvider.getCredential(token.token)
//            val resource = authDataSource.signInUsingCredential(credential)
//            val userDTO = resource.data?.let {
//                getUserDTOFromFirebaseUser(it)
//            } ?: UserDTO()
//            return@withContext storeUserDataAfterRegister(resource, userDTO)
//        }

//    suspend fun loginUsingFacebook(token: AccessToken): Resource<Unit> =
//        withContext(Dispatchers.IO) {
//            val credential = FacebookAuthProvider.getCredential(token.token)
//            val resource = authDataSource.signInUsingCredential(credential)
//            return@withContext getUserDataAfterLogin(resource, resource.data!!.email!!)
//        }

    private suspend fun logoutUser() {
        authDataSource.logoutUser()
        preferencesDataSource.removeUserData();
    }

    private suspend fun removeUser() {
        authDataSource.removeUser();
        preferencesDataSource.removeUserData()
    }

    private fun getUserDTOFromFirebaseUser(firebaseUser: FirebaseUser) = UserDTO(
        email = firebaseUser.email.toString(),
        username = firebaseUser.displayName.toString(),
        profile_img = firebaseUser.photoUrl.toString()
    )

    private suspend fun <T> getUserDataAfterLogin(
        authResource: Resource<T>,
        email: String
    ): Resource<Unit> {
        val harperResource = harperDbAuthDataSource.getUserData(email)
        if (harperResource is Resource.Error || authResource is Resource.Error) {
            if (harperResource is Resource.Error && authResource is Resource.Success) {
                if (harperResource.message == "User does not exist") removeUser() else logoutUser()
                return Resource.Error(message = harperResource.message)
            }
            return Resource.Error(message = "Failed to login user")
        }
        preferencesDataSource.saveUserData(userMapper.toDomainModel(harperResource.data!!))
        return Resource.Success(message = "User logged in successfully")
    }


    private suspend fun <T> storeUserDataAfterRegister(
        resource: Resource<T>,
        userDTO: UserDTO,
    ): Resource<Unit> {
        val harperResource = harperDbAuthDataSource.storeUserIntoDb(userDTO)
        if (harperResource is Resource.Error || resource is Resource.Error) {
            if (harperResource is Resource.Error && resource is Resource.Success) {
                removeUser()
            }
            return Resource.Error(message = "Failed to register user")
        }
        preferencesDataSource.saveUserData(userMapper.toDomainModel(userDTO))
        return Resource.Success(message = "User registered successfully")
    }


}