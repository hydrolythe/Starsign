package com.example.starsign.card

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.starsign.R
import com.example.starsign.carddetail.ManaDetailViewHolder
import com.example.starsign.carddetail.MonsterDetailFragment
import com.example.starsign.carddetail.MonsterDetailFragmentDirections
import com.example.starsign.carddetail.SpellDetailViewHolder
import com.example.starsign.cardformulars.AttributeViewHolder
import com.example.starsign.cardformulars.CardCreatorViewModel
import com.example.starsign.database.*
import com.example.starsign.fakeCardModule
import com.example.starsign.listDbCards
import com.example.starsign.typeofcardmenu.TypeOfCardFragmentDirections
import com.example.starsign.withRecyclerViewText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class MonsterDetailTest {
    private lateinit var scenario: FragmentScenario<MonsterDetailFragment>
    private lateinit var navController: NavController
    private lateinit var bundle : Bundle
    private val title : String = "Epeak"
    private val manamap = mapOf(Pair(Mana.ATOM, 5))
    private val life = 10
    private val attack = 3
    private val defense = 5
    private val magicattack = 7
    private val magicdefense = 2
    private val mp = 15
    private val spellmap = mapOf(Pair(Spell.DRAW, 2))
    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", Monster(title,manamap,life,attack,defense,magicattack,magicdefense,mp,spellmap))
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<MonsterDetailFragment>(
            bundle,
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun checkIfMonsterIsCorrectlyLoaded(){
        onView(withId(R.id.titlelabel)).check(matches(withText(title)))
        onView(withId(R.id.healthtext)).check(matches(withText(life.toString())))
        onView(withId(R.id.attacktext)).check(matches(withText(attack.toString())))
        onView(withId(R.id.defensetext)).check(matches(withText(defense.toString())))
        onView(withId(R.id.magicattacktext)).check(matches(withText(magicattack.toString())))
        onView(withId(R.id.magicdefensetext)).check(matches(withText(magicdefense.toString())))
        onView(withId(R.id.mptext)).check(matches(withText(mp.toString())))
    }

    @Test
    fun onNavigation_navigatesToCorrectFragment(){
        onView(withId(R.id.editbutton)).perform(click())
        Mockito.verify(navController).navigate(
            MonsterDetailFragmentDirections.actionMonsterDetailFragmentToTrueMonsterFragment(listDbCards[3] as NetworkMonster)
        )
    }
}