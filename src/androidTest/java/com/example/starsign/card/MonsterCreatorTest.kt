package com.example.starsign.card

import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.starsign.R
import com.example.starsign.ToastMatcher
import com.example.starsign.cardformulars.AttributeViewHolder
import com.example.starsign.cardformulars.EffectViewHolder
import com.example.starsign.cardformulars.MonsterCreatorFragment
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.example.starsign.fakeCardModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
class MonsterCreatorTest {
    private lateinit var scenario: FragmentScenario<MonsterCreatorFragment>

    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        scenario = launchFragmentInContainer<MonsterCreatorFragment>(Bundle(),
            R.style.AppTheme
        )
    }

    @Test
    fun createMonster_CorrectMonster_givesCorrectMessageAndAddsItToMessages(){
        onView(withId(R.id.titletextfield)).perform(replaceText("Herculitos"))
        onView(withId(R.id.manaselections)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AttributeViewHolder>(0, click()))
        onView(withId(R.id.lifetextfield)).perform(replaceText("10"))
        onView(withId(R.id.attacktextfield)).perform(replaceText("5"))
        onView(withId(R.id.defensetextfield)).perform(replaceText("3"))
        onView(withId(R.id.magicattacktextfield)).perform(replaceText("2"))
        onView(withId(R.id.magicdefensefield)).perform(replaceText("3"))
        onView(withId(R.id.mptextfield)).perform(replaceText("2"))
        onView(withId(R.id.spellfield)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EffectViewHolder>(0, click())
        )
        onView(withId(R.id.submitmonster)).perform(click())
        onView(withText(String.format("Monster %s was successfully created.",
            "Herculitos"
        ))).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }
}