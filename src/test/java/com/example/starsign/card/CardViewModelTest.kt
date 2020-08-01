package com.example.starsign.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starsign.LiveDataTestUtil
import com.example.starsign.MainCoroutineRule
import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockRepository.FakeCardRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CardViewModelTest {
    private lateinit var cardRepository : FakeCardRepository
    private lateinit var cardViewModel : CardViewModel
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
            manas = mapOf(Pair(Mana.APEIRON, 3))
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

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        cardRepository = FakeCardRepository(listDbCards)
        cardViewModel = CardViewModel(cardRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun haalKaartenOp_RetourneertAanwezigeKaarten(){
        assertThat(LiveDataTestUtil.getValue(cardViewModel.cardList).size, `is`(listDbCards.size))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun verwijderKaarten_FouteCollectie_SlaatFoutOp(){
        val fakeCards = listOf(
            Monster(title="Irrat", manarequirements = mapOf(Pair(Mana.VOID, 2)), life=15, attack = 5, defense = 5, magicdefense = 8, magicattack = 10, mp = 5, spells = null),
            Magic(title="Void", manaamount = mapOf(Pair(Mana.TIME, 1), Pair(Mana.VOID, 2)), species = SpellSpecies.EVENT, spells = mapOf(Pair(Spell.DRAW, 2)))
        )
        cardViewModel.deleteCards(fakeCards)
        assertThat(LiveDataTestUtil.getValue(cardViewModel.cardResult).exception, not(nullValue()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun verwijderKaarten_JuisteCollectie_GeeftJuisteverzamelingterug(){
        val trueCards = listOf(
            listDbCards[0],
            listDbCards[1]
        ).asDomainModel()
        val size1 = listDbCards.size
        cardViewModel.deleteCards(trueCards)
        assertThat(LiveDataTestUtil.getValue(cardViewModel.cardResult).success?.size, `is`(trueCards.size))
        assertThat(LiveDataTestUtil.getValue(cardViewModel.cardList).size, `is`(size1-trueCards.size))
    }
}