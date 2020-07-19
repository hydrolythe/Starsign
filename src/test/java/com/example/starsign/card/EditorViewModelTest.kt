package com.example.starsign.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starsign.MainCoroutineRule
import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockRepository.FakeCardRepository
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class EditorViewModelTest {
    private lateinit var cardRepository : FakeCardRepository
    private lateinit var viewModel: EditorViewModel
    private var listDbCards = mutableListOf(
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
                Pair(Spell.DESTROYFIELD, 2)
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
                Pair(Spell.DESTROYFIELD, 2)
            )
        )
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        cardRepository = FakeCardRepository(listDbCards)
        viewModel = EditorViewModel(cardRepository)
    }
}