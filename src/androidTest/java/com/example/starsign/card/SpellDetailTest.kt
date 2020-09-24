package com.example.starsign.card

import android.os.Bundle
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
import com.example.starsign.carddetail.*
import com.example.starsign.database.*
import com.example.starsign.fakeCardModule
import com.example.starsign.listDbCards
import com.example.starsign.network.NetworkMagic
import com.example.starsign.withRecyclerViewText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class SpellDetailTest {
    private lateinit var scenario: FragmentScenario<SpellDetailFragment>
    private lateinit var navController: NavController
    private lateinit var bundle : Bundle
    private val title = "Sword"
    private val species = SpellSpecies.EQUIPMENT
    private val manaamount = mapOf(Pair(Mana.ATOM, 3))
    private val spells = mapOf(Pair(Spell.BOOSTATTACK, 2))

    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", Magic(title, species, spells, manaamount))
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<SpellDetailFragment>(
            bundle,
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun CheckIfSpellIsCorrectlyLoaded(){
        onView(withId(R.id.titlelabel)).check(matches(withText(title)))
        onView(withId(R.id.spelltypetext)).check(matches(withText(species.toString())))
        manaamount.keys.forEach{
            onView(withId(R.id.costlist)).perform(RecyclerViewActions.scrollToPosition<ManaDetailViewHolder>(0))
            onView(withId(R.id.costlist)).check(matches(withRecyclerViewText(0, R.id.mananame, it.toString())))
            onView(withId(R.id.costlist)).check(matches(withRecyclerViewText(0, R.id.amountlabel, manaamount[it].toString())))
        }
        spells.keys.forEach{
            onView(withId(R.id.effectlist)).perform(RecyclerViewActions.scrollToPosition<SpellDetailViewHolder>(0))
            onView(withId(R.id.effectlist)).check(matches(withRecyclerViewText(0, R.id.spellname, it.toString())))
            onView(withId(R.id.effectlist)).check(matches(withRecyclerViewText(0, R.id.powerlabel, spells[it].toString())))
        }
    }

    @Test
    fun onClick_GoesToEditScreen(){
        onView(withId(R.id.edittextbutton)).perform(click())
        Mockito.verify(navController).navigate(
            SpellDetailFragmentDirections.actionSpellDetailFragmentToSpellEditorFragment(listDbCards[1] as NetworkMagic)
        )
    }
}