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
import com.example.starsign.withRecyclerViewText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class SourceDetailTest {
    private lateinit var scenario: FragmentScenario<SourceDetailFragment>
    private lateinit var navController: NavController
    private lateinit var bundle : Bundle
    private val title = "Miletus"
    private val source = mapOf(Pair(Mana.APEIRON, 3))

    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", Source(title, source))
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<SourceDetailFragment>(
            bundle,
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun checkIfSourceIsCorrectlyLoaded(){
        onView(withId(R.id.manatitletext)).check(matches(withText(title)))
        source.keys.forEach{
            onView(withId(R.id.manageneratorlist)).perform(RecyclerViewActions.scrollToPosition<ManaDetailViewHolder>(0))
            onView(withId(R.id.manageneratorlist)).check(matches(withRecyclerViewText(0, R.id.mananame, it.toString())))
            onView(withId(R.id.manageneratorlist)).check(matches(withRecyclerViewText(0, R.id.amountlabel, source[it].toString())))
        }
    }

    @Test
    fun onClick_goesToEditScreen(){
        onView(withId(R.id.editbutton)).perform(click())
        Mockito.verify(navController).navigate(
            SourceDetailFragmentDirections.actionSourceDetailFragmentToSourceEditorFragment(listDbCards[2] as NetworkSource)
        )
    }
}