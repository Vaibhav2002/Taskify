package com.vaibhav.taskify.ui.auth.register

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.taskify.data.repo.AuthRepo
import com.vaibhav.taskify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _registerState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val registerState: StateFlow<Resource<Unit>> = _registerState

    fun onEmailTextChange(email: String) {
        _email.value = email
    }

    fun onPasswordTextChange(password: String) {
        _password.value = password
    }

    fun onUsernameTextChange(username: String) {
        _username.value = username
    }

    private fun verifyUserInput(): Boolean {
        val regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        return _email.value.matches(Regex(regex)) && _password.value.isNotEmpty() && _username.value.isNotEmpty()
    }

    fun onRegisterButtonPressed() = viewModelScope.launch {
        if (verifyUserInput()) {
            _registerState.emit(Resource.Loading())
            _registerState.emit(
                authRepo.registerUser(
                    _email.value,
                    _username.value,
                    _password.value
                )
            )
        } else
            _registerState.emit(Resource.Error("Enter details correctly"))
    }

    fun onGoogleRegisterPressed(data: Intent) = viewModelScope.launch {
        _registerState.emit(Resource.Loading())
        _registerState.emit(authRepo.registerUsingGoogle(data))
    }
}
