package com.example.starsign.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.data.Resulting
import com.example.starsign.network.UserDto
import com.example.starsign.repository.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(val repository: IUserRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _jwtResponse = MutableLiveData<JwtTokenResult>()
    val jwtResponse: LiveData<JwtTokenResult> = _jwtResponse

    fun voegRegistratieToe(username: String, password: String, matchingPassword: String) {
        uiScope.launch {
            val result = repository.addUser(UserDto(username, password, matchingPassword))
            if(result is Resulting.Success){
                _jwtResponse.value = JwtTokenResult(success=result.data)
            }
            if(result is Resulting.Error){
                _jwtResponse.value = JwtTokenResult(error= result.exception)
            }
        }
    }
}