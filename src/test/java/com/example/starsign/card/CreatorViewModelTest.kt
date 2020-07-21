package com.example.starsign.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starsign.LiveDataTestUtil
import com.example.starsign.MainCoroutineRule
import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.cardformulars.CardCreatorViewModel
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockRepository.FakeCardRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CreatorViewModelTest {
    private lateinit var cardRepository : FakeCardRepository
    private lateinit var viewModel: CardCreatorViewModel
    private lateinit var cvm : CardViewModel
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
        cvm = CardViewModel(cardRepository)
        viewModel = CardCreatorViewModel(cardRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addCard_BadInput_ReturnsErrorMessage(){
        val existingCard = listDbCards[1]
        val size1 = listDbCards.size
        viewModel.insertCard(existingCard.asDomainModel())
        assertThat(LiveDataTestUtil.getValue(viewModel.cardCreationResult).exception, not(nullValue()))
        assertThat(LiveDataTestUtil.getValue(cvm.cardList).size, `is`(size1))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addCard_CorrectInput_ReturnsCorrectMessageAndPersists(){
        val newCard = Monster(title="Chaos", manarequirements = mapOf(Pair(Mana.VOID, 5)), life=30, attack = 10, defense = 5, magicdefense = 15, magicattack = 20, mp = 50, spells = mapOf(Pair(Spell.DAMAGE, 3), Pair(Spell.DESTROYFIELD, 5), Pair(Spell.DRAW, 15)))
        val size1 = listDbCards.size
        viewModel.insertCard(newCard)
        assertThat(LiveDataTestUtil.getValue(viewModel.cardCreationResult).success?.title, `is`(newCard.title))
        assertThat(LiveDataTestUtil.getValue(cvm.cardList).size, `is`(size1+1))
    }
}