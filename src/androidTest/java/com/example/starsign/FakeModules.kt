package com.example.starsign

import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.cardformulars.CardCreatorViewModel
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.mockRepository.FakeUserRepository
import com.example.starsign.register.RegisterViewModel
import com.example.starsign.repository.ICardRepository
import com.example.starsign.repository.IUserRepository
import com.example.starsign.ui.login.LoginViewModel
import mockRepository.FakeCardRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fakeUserModule = module{
    single<IUserRepository>{ FakeUserRepository() }
    viewModel{LoginViewModel(get())}
    viewModel{RegisterViewModel(get())}
}
var listDbCards = mutableListOf(
    DatabaseMonster(
        cardid = 0,
        title = "Salamaximander",
        manarequirements = mapOf(Pair(Mana.APEIRON, 3)),
        life = 5,
        attack = 3,
        defense = 2,
        magicattack = 5,
        magicdefense = 2,
        mp = 10,
        spells = mapOf(
            Pair(Spell.BOOSTSPECIALATTACK, 3),
            Pair(Spell.DRAW, 2)
        )
    ),
    DatabaseMagic(
        cardid = 1,
        title = "Sword",
        species = SpellSpecies.EQUIPMENT,
        manaamount = mapOf(Pair(Mana.ATOM, 3)),
        spells = mapOf(Pair(Spell.BOOSTATTACK, 2))
    ),
    DatabaseSource(
        cardid = 3,
        title = "Miletus",
        source = mapOf(Pair(Mana.APEIRON, 3))
    ),
    DatabaseMonster(
        cardid = 4,
        title = "Epeak",
        manarequirements = mapOf(Pair(Mana.ATOM, 5)),
        life = 10,
        attack = 3,
        defense = 5,
        magicattack = 7,
        magicdefense = 2,
        mp = 15,
        spells = mapOf(
            Pair(Spell.DRAW, 2)
        )
    )
)
val fakeCardModule = module{
    single<ICardRepository>{FakeCardRepository(listDbCards)}
    viewModel{CardViewModel(get())}
    viewModel{CardCreatorViewModel(get())}
    viewModel{EditorViewModel(get())}
}