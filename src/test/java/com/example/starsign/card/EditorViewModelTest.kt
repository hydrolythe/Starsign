package com.example.starsign.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starsign.LiveDataTestUtil
import com.example.starsign.MainCoroutineRule
import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.network.NetworkCard
import com.example.starsign.network.NetworkMagic
import com.example.starsign.network.NetworkMonster
import com.example.starsign.network.NetworkSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockRepository.FakeCardRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EditorViewModelTest {
    private lateinit var cardRepository : FakeCardRepository
    private lateinit var viewModel: EditorViewModel
    private lateinit var cvm : CardViewModel
    private var listDbCards = mutableListOf(
        NetworkMonster(
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
        NetworkMagic(
            cardid = 1,
            title = "Sword",
            species = SpellSpecies.EQUIPMENT,
            manaamount = mapOf(Pair(Mana.ATOM, 3)),
            spells = mapOf(Pair(Spell.BOOSTATTACK, 2))
        ),
        NetworkSource(
            cardid = 3,
            title = "Miletus",
            manas = mapOf(Pair(Mana.APEIRON, 3))
        ),
        NetworkMonster(
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

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        cardRepository = FakeCardRepository(listDbCards)
        cvm = CardViewModel(cardRepository)
        viewModel = EditorViewModel(cardRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDbCard_ValidTitle_ReturnsDbCard(){
        val selectedCard = listDbCards[1]
        val title = selectedCard.title
        viewModel.getDbCard<NetworkCard>(title)
        assertThat(LiveDataTestUtil.getValue(viewModel.dbCardResult).success, `is`(selectedCard))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDbCard_InvalidTitle_ReturnsNull(){
        val fakeCard = Monster(title="Irrat", manarequirements = mapOf(Pair(Mana.VOID, 2)), life=15, attack = 5, defense = 5, magicdefense = 8, magicattack = 10, mp = 5, spells = null)
        val title = fakeCard.title
        viewModel.getDbCard<NetworkCard>(title)
        assertThat(LiveDataTestUtil.getValue(viewModel.dbCardResult).exception, not(nullValue()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateCard_invalidId_returnsIncorrectResultAndDoesNotUpdate(){
        val fakeCard = NetworkMonster(cardid = 10, title="Irrat", manarequirements = mapOf(Pair(Mana.VOID, 2)), life=15, attack = 5, defense = 5, magicdefense = 8, magicattack = 10, mp = 5, spells = null)
        viewModel.updateCard(fakeCard)
        assertThat(LiveDataTestUtil.getValue(viewModel.cardEditResult).exception, not(nullValue()))
        assertThat(LiveDataTestUtil.getValue(viewModel.newCard), nullValue())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateCard_validId_returnsCorrectResultAndDoesUpdate(){
        val trueCard = NetworkMonster(
            cardid = 0,
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
        viewModel.updateCard(trueCard)
        assertThat(LiveDataTestUtil.getValue(viewModel.cardEditResult).success, not(nullValue()))
        assertThat(LiveDataTestUtil.getValue(viewModel.newCard), `is`(trueCard.asDomainModel()))
    }
}