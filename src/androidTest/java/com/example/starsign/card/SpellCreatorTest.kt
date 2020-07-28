package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.starsign.MyViewAction
import com.example.starsign.R
import com.example.starsign.ToastMatcher
import com.example.starsign.cardformulars.*
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.example.starsign.fakeCardModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
class SpellCreatorTest {
    private lateinit var scenario: FragmentScenario<SpellCreator>

    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        scenario = launchFragmentInContainer<SpellCreator>(Bundle(),
            R.style.AppTheme
        )
    }

    @Test
    fun MakeSpell_CorrectSpell_ReturnsCorrectMessage(){
        onView(withId(R.id.spelltitletext)).perform(replaceText("Handsome"))
        onView(withId(R.id.spellspeciesoptions)).check(matches(isDisplayed()))
        onView(withId(R.id.spellspeciesoptions)).perform(RecyclerViewActions.actionOnItemAtPosition<SpellspeciesViewHolder>(0, click()))
        onView(withId(R.id.manacost)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(1)
        )
        onView(withId(R.id.manacost)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AttributeViewHolder>(2, MyViewAction.typeOnViewWithId(R.id.manaamounttext, 1))
        )
        onView(withId(R.id.effectspells)).perform(
            RecyclerViewActions.scrollToPosition<EffectViewHolder>(1)
        )
        onView(withId(R.id.effectspells)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EffectViewHolder>(1, MyViewAction.typeOnViewWithId(R.id.spellamounttext, 1))
        )
        onView(withId(R.id.addspellbutton)).perform(click())
        onView(withText(String.format("Spell %s was successfully created.",
            "Handsome"
        ))).inRoot(
            ToastMatcher()
        ).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.manacost)).check(matches(
            com.example.starsign.withText(
                1,
                R.id.manaamounttext,
                0.toString()
            )
        ))
    }

    @Test
    fun MakeSpell_Incorrect_PreservesError(){
        onView(withId(R.id.manacost)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(2)
        )
        onView(withId(R.id.manacost)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AttributeViewHolder>(2, MyViewAction.typeOnViewWithId(R.id.manaamounttext, 2))
        )
        onView(withId(R.id.addspellbutton)).perform(click())
        onView(withId(R.id.manacost)).check(matches(
            com.example.starsign.withText(
                2,
                R.id.manaamounttext,
                2.toString()
            )
        ))
    }
}