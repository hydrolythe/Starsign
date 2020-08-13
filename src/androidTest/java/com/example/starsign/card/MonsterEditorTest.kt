package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.starsign.R
import com.example.starsign.cardformulars.MonsterEditorFragment
import com.example.starsign.cardformulars.MonsterEditorFragmentDirections
import com.example.starsign.database.*
import com.example.starsign.fakeCardModule
import com.example.starsign.listDbCards
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class MonsterEditorTest {
    private lateinit var scenario: FragmentScenario<MonsterEditorFragment>
    private lateinit var navController: NavController
    private lateinit var bundle : Bundle
    private val title : String = "Epeak"
    private val life = 10
    private val attack = 3
    private val defense = 5
    private val magicattack = 7
    private val magicdefense = 2
    private val mp = 15
    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", listDbCards[3] as NetworkMonster)
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<MonsterEditorFragment>(
            bundle,
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun checkIfMonsterIsCorrectlyLoaded(){
        Espresso.onView(ViewMatchers.withId(R.id.titletextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(title)))
        Espresso.onView(ViewMatchers.withId(R.id.lifetextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(life.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.attacktextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(attack.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.defensetextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(defense.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.magicattacktextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(magicattack.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.magicdefensefield))
            .check(ViewAssertions.matches(ViewMatchers.withText(magicdefense.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.mptextfield))
            .check(ViewAssertions.matches(ViewMatchers.withText(mp.toString())))
    }

    @Test
    fun onNavigation_navigatesToCorrectFragment(){
        Espresso.onView(ViewMatchers.withId(R.id.submitmonster)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            MonsterEditorFragmentDirections.actionMonsterEditorFragmentToTrueMonsterEditorFragment(
                ProtoMonster(title, life, attack, defense, magicattack, magicdefense, mp), listDbCards[3] as NetworkMonster
            )
        )
    }
}