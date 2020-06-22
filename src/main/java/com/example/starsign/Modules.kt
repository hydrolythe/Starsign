package com.example.starsign

import com.example.starsign.cardcreator.CardCreatorViewModel
import com.example.starsign.register.RegisterViewModel
import com.example.starsign.repository.*
import com.example.starsign.ui.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module{
    single<ICardRepository> { CardRepository() }
    viewModel{CardCreatorViewModel(get())}
}

val userModule = module{
    single<IUserRepository>{ UserRepository() }
    viewModel{LoginViewModel(get())}
    viewModel{RegisterViewModel(get())}
}