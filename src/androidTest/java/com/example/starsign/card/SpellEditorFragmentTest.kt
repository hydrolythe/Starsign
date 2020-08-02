package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.starsign.*
import com.example.starsign.cardformulars.*
import com.example.starsign.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class SpellEditorFragmentTest {
    private lateinit var scenario: FragmentScenario<SpellEditorFragment>
    private lateinit var bundle : Bundle
    private val cardid = 1L
    private val title = "Sword"
    private val species = SpellSpecies.EQUIPMENT
    private val manaamount = mapOf(Pair(Mana.ATOM,3))
    private val spells = mapOf(Pair(Spell.BOOSTATTACK, 2))

    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", DatabaseMagic(cardid, title, species, spells, manaamount))
        scenario = launchFragmentInContainer<SpellEditorFragment>(
            bundle,
            R.style.AppTheme
        )
    }

    @Test
    fun MakeSpell_Incorrect_PreservesRest() {
        Espresso.onView(ViewMatchers.withId(R.id.spelltitletext)).check(matches(
            isEditTextValueEqualTo(title)
        ))
        Espresso.onView(ViewMatchers.withId(R.id.spelltitletext)).perform(clearText())
        Espresso.onView(ViewMatchers.withId(R.id.addspellbutton)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.spellspeciesoptions)).perform(
            RecyclerViewActions.scrollToPosition<SpellspeciesViewHolder>(SpellSpecies.EQUIPMENT.ordinal)
        )
        Espresso.onView(ViewMatchers.withId(R.id.spellspeciesoptions)).check(
            matches(withRecyclerViewCheck(SpellSpecies.EQUIPMENT.ordinal, R.id.spellitem, true))
        )
        Espresso.onView(ViewMatchers.withId(R.id.manacost)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(Mana.ATOM.ordinal)
        )
        Espresso.onView(ViewMatchers.withId(R.id.manacost)).check(
            matches(
                withRecyclerViewText(
                    Mana.ATOM.ordinal,
                    R.id.manaamounttext,
                    3.toString()
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.effectspells)).perform(
            RecyclerViewActions.scrollToPosition<EffectViewHolder>(Spell.BOOSTATTACK.ordinal)
        )
        Espresso.onView(ViewMatchers.withId(R.id.effectspells)).check(
            matches(
                withRecyclerViewText(
                    Spell.BOOSTATTACK.ordinal,
                    R.id.spellamounttext,
                    2.toString()
                )
            )
        )
    }
    @Test
    fun MakeSpell_Correct_SaysEdited(){
        Espresso.onView(ViewMatchers.withId(R.id.addspellbutton)).perform(click())
        Espresso.onView(
            withText(
                String.format(
                    "Successful edit"
                )
            )
        ).inRoot(ToastMatcher()).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}