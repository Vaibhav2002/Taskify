package com.vaibhav.taskify.ui.auth.login

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
class LoginViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val loginState: StateFlow<Resource<Unit>> = _loginState


    fun onEmailTextChange(email: String) {
        _email.value = email
    }

    fun onPasswordTextChange(password: String) {
        _password.value = password
    }

    private fun verifyUserInput(): Boolean {
        val regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        return _email.value.matches(Regex(regex)) && _password.value.isNotEmpty()
    }

    fun onLoginButtonPressed() = viewModelScope.launch {
        if (verifyUserInput()) {
            _loginState.emit(Resource.Loading())
            _loginState.emit(authRepo.loginUser(_email.value, _password.value))
        } else
            _loginState.emit(Resource.Error("Enter details correctly"))
    }

    fun onGoogleLoginPressed(data: Intent) = viewModelScope.launch {
        _loginState.emit(Resource.Loading())
        _loginState.emit(authRepo.loginUsingGoogle(data))
    }


}