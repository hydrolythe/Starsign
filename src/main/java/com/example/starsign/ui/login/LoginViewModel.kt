package com.example.starsign.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.example.starsign.R
import com.example.starsign.data.Resulting
import com.example.starsign.network.JwtRequest
import com.example.starsign.register.JwtTokenResult
import com.example.starsign.repository.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: IUserRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<JwtTokenResult>()
    val loginResult: LiveData<JwtTokenResult> = _loginResult

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun login(username: String, password: String) {
        uiScope.launch {
            val result = userRepository.loginUser(JwtRequest(username, password))
            if (result is Resulting.Success) {
                _loginResult.value =
                    JwtTokenResult(success = result.data)
            }
            if(result is Resulting.Error){
                _loginResult.value = JwtTokenResult(error = result.exception)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
