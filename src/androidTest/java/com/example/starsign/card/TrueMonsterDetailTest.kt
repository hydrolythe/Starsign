package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.starsign.R
import com.example.starsign.carddetail.*
import com.example.starsign.database.*
import com.example.starsign.fakeCardModule
import com.example.starsign.listDbCards
import com.example.starsign.network.NetworkMonster
import com.example.starsign.withRecyclerViewText
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

class TrueMonsterDetailTest {
    private lateinit var scenario: FragmentScenario<TrueMonsterFragment>
    private lateinit var navController: NavController
    private lateinit var bundle : Bundle
    private val manamap = mapOf(Pair(Mana.ATOM, 5))
    private val spellmap = mapOf(Pair(Spell.DRAW, 2))
    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", listDbCards[3] as NetworkMonster)
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<TrueMonsterFragment>(
            bundle,
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun checkIfMonsterIsCorrectlyLoaded() {
        manamap.keys.forEach{
            Espresso.onView(ViewMatchers.withId(R.id.manacost))
                .perform(RecyclerViewActions.scrollToPosition<ManaDetailViewHolder>(0))
            Espresso.onView(ViewMatchers.withId(R.id.manacost)).check(
                ViewAssertions.matches(
                    withRecyclerViewText(
                        0,
                        R.id.mananame,
                        it.toString()
                    )
                )
            )
            Espresso.onView(ViewMatchers.withId(R.id.manacost)).check(
                ViewAssertions.matches(
                    withRecyclerViewText(
                        0,
                        R.id.amountlabel,
                        manamap[it].toString()
                    )
                )
            )
        }
        spellmap.keys.forEach{
            Espresso.onView(ViewMatchers.withId(R.id.effects))
                .perform(RecyclerViewActions.scrollToPosition<SpellDetailViewHolder>(0))
            Espresso.onView(ViewMatchers.withId(R.id.effects)).check(
                ViewAssertions.matches(
                    withRecyclerViewText(
                        0,
                        R.id.spellname,
                        it.toString()
                    )
                )
            )
            Espresso.onView(ViewMatchers.withId(R.id.effects)).check(
                ViewAssertions.matches(
                    withRecyclerViewText(
                        0,
                        R.id.powerlabel,
                        spellmap[it].toString()
                    )
                )
            )
        }
    }

    @Test
    fun onNavigation_navigatesToCorrectFragment(){
        Espresso.onView(ViewMatchers.withId(R.id.submit)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            TrueMonsterFragmentDirections.actionTrueMonsterFragmentToMonsterEditorFragment(
                listDbCards[3] as NetworkMonster
            )
        )
    }
}