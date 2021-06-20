package com.vaibhav.taskify.data.remote


import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vaibhav.taskify.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun logoutUser() = auth.signOut()
    
    fun isUserLoggedIn() = auth.currentUser !=null

    suspend fun loginUser(email: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(message = "User signed in successfully")
            } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
            }
        }

    suspend fun removeUser() {
        auth.currentUser?.delete()
    }


    suspend fun registerUser(email: String, username: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.createUserWithEmailAndPassword(email, password).await()
                Resource.Success(message = "User signed up successfully")
            } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
            }
        }

    suspend fun signInUsingCredential(credential: AuthCredential) = withContext(Dispatchers.IO) {
        return@withContext try {
            auth.signInWithCredential(credential).await()
            Resource.Success<FirebaseUser>(
                data = auth.currentUser,
                message = "User signed up successfully"
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getGoogleAccount(data: Intent): Resource<GoogleSignInAccount> =
        withContext(Dispatchers.IO) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                Resource.Success(data = task)
            } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
            }
        }

}