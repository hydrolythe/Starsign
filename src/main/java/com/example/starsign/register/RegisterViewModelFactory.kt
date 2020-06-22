package com.example.starsign.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starsign.repository.IUserRepository
import java.lang.IllegalArgumentException

class RegisterViewModelFactory(val repository: IUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            @Suppress("UNCHECKED CAST")
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel") as Throwable
    }
}