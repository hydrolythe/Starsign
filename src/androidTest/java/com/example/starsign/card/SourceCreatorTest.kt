package com.example.starsign.card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.starsign.*
import com.example.starsign.cardcreator.CardCreatorFragment
import com.example.starsign.cardformulars.AttributeViewHolder
import com.example.starsign.cardformulars.SourceCreator
import com.example.starsign.database.Mana
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.StringDescription
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


@ExperimentalCoroutinesApi
class SourceCreatorTest {
    private lateinit var scenario: FragmentScenario<SourceCreator>

    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        scenario = launchFragmentInContainer<SourceCreator>(Bundle(),
            R.style.AppTheme
        )
    }

    @Test
    fun MakeSource_Correct_ReturnsSource(){
        onView(withId(R.id.sourcetitletext)).perform(replaceText("Thebes"))
        onView(withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(2)
        )
        onView(withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AttributeViewHolder>(2, MyViewAction.typeOnViewWithId(R.id.manaamounttext, 2))
        )
        onView(withId(R.id.sourcecreatorbutton)).perform(click())
        onView(
            withText(
                String.format(
                    "Source %s was successfully created.",
                    "Thebes"
                )
            )
        ).inRoot(ToastMatcher()).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.sourcetitletext)).check(matches(hasNoText()))
        onView(withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(2)
        )
        onView(withId(R.id.sourcetypes)).check(matches(withText(2, R.id.manaamounttext, 0.toString())))
    }

    @Test
    fun MakeSource_Incorrect_PreservesError(){
        onView(withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(2)
        )
        onView(withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AttributeViewHolder>(2, MyViewAction.typeOnViewWithId(R.id.manaamounttext, 2))
        )
        onView(withId(R.id.sourcetypes)).check(matches(withText(2, R.id.manaamounttext, 2.toString())))
    }
}