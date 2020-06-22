package com.example.starsign

import com.example.starsign.mockRepository.FakeUserRepository
import com.example.starsign.register.RegisterViewModel
import com.example.starsign.repository.IUserRepository
import com.example.starsign.ui.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fakeUserModule = module{
    single<IUserRepository>{ FakeUserRepository() }
    viewModel{LoginViewModel(get())}
    viewModel{RegisterViewModel(get())}
}