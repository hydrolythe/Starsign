package com.example.starsign

import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.cardformulars.CardCreatorViewModel
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.StarsignDatabase
import com.example.starsign.register.RegisterViewModel
import com.example.starsign.repository.*
import com.example.starsign.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module{
    single<ICardRepository> { CardRepository(StarsignDatabase.getInstance(this.androidContext())) }
    viewModel{ CardCreatorViewModel(get()) }
    viewModel{EditorViewModel(get())}
    viewModel{CardViewModel(get())}
}

val userModule = module{
    single<IUserRepository>{ UserRepository() }
    viewModel{LoginViewModel(get())}
    viewModel{RegisterViewModel(get())}
}