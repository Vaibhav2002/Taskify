package com.vaibhav.taskify.data.repo


import android.content.Intent
import android.net.Uri
import android.os.UserManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.vaibhav.taskify.data.local.PreferencesDataSource
import com.vaibhav.taskify.data.models.entity.UserEntity
import com.vaibhav.taskify.data.models.mappper.UserMapper
import com.vaibhav.taskify.data.remote.FirebaseAuthDataSource
import com.vaibhav.taskify.data.remote.harperDb.HarperDbAuthDataSource
import com.vaibhav.taskify.data.remote.harperDb.User
import com.vaibhav.taskify.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
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
            Timber.d((resource is Resource.Success).toString())
            val harperResource = harperDbAuthDataSource.getUserData(email)
            Timber.d((harperResource is Resource.Success).toString())
            if (harperResource is Resource.Error || resource is Resource.Error) {
                if (harperResource is Resource.Error && resource is Resource.Success)
                    logoutUser()
                return@withContext Resource.Error(message = "Failed to login user")
            }
            preferencesDataSource.saveUserData(userMapper.toDomainModel(harperResource.data!!))
            return@withContext Resource.Success(message = "User logged in successfully")
        }

    suspend fun registerUser(email: String, username: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val resource =
                authDataSource.registerUser(email, username = username, password = password)
            val user = User(email = email, username = username)
            val harperResource = harperDbAuthDataSource.storeUserIntoDb(user)
            if (harperResource is Resource.Error || resource is Resource.Error) {
                if (harperResource is Resource.Error && resource is Resource.Success) {
                    authDataSource.removeUser()
                }
                return@withContext Resource.Error(message = "Failed to register user")
            }
            preferencesDataSource.saveUserData(userMapper.toDomainModel(user))
            return@withContext Resource.Success(message = "User registered successfully")
        }


    suspend fun registerUsingUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error)
                return@withContext Resource.Error("Failed to sign in using Google")
            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            val user = resource.data?.let {
                User(
                    email = it.email.toString(),
                    username = it.displayName.toString(),
                    profile_img = it.photoUrl.toString()
                )
            } ?: User()
            val harperResource = harperDbAuthDataSource.storeUserIntoDb(user)
            if (harperResource is Resource.Error || resource is Resource.Error) {
                if (harperResource is Resource.Error && resource is Resource.Success) {
                    authDataSource.removeUser()
                }
                return@withContext Resource.Error(message = "Failed to register user")
            }
            preferencesDataSource.saveUserData(userMapper.toDomainModel(user))
            return@withContext Resource.Success(message = "User registered successfully")
        }


    suspend fun loginUsingUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error)
                return@withContext Resource.Error("Failed to sign in using Google")
            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            val harperResource = harperDbAuthDataSource.getUserData(resource.data!!.email!!)
            if (harperResource is Resource.Error || resource is Resource.Error) {
                if (harperResource is Resource.Error && resource is Resource.Success)
                    logoutUser()
                return@withContext Resource.Error(message = "Failed to login user")
            }
            preferencesDataSource.saveUserData(userMapper.toDomainModel(harperResource.data!!))
            return@withContext Resource.Success(message = "User logged in successfully")
        }


    suspend fun logoutUser() {
        authDataSource.logoutUser()
        preferencesDataSource.removeUserData();
    }
}