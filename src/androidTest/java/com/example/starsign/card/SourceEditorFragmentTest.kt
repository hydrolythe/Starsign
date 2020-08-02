package com.example.starsign.card

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.starsign.*
import com.example.starsign.carddetail.SourceDetailFragment
import com.example.starsign.cardformulars.AttributeViewHolder
import com.example.starsign.cardformulars.SourceEditorFragment
import com.example.starsign.database.DatabaseSource
import com.example.starsign.database.Mana
import com.example.starsign.database.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
class SourceEditorFragmentTest {
    private lateinit var scenario: FragmentScenario<SourceEditorFragment>
    private lateinit var bundle : Bundle
    private val title = "Miletus"
    private val source = mapOf(Pair(Mana.APEIRON, 3))
    private val id = 2L

    @Before
    fun before(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        bundle = Bundle()
        bundle.putParcelable("card", DatabaseSource(id, title, source))
        scenario = launchFragmentInContainer<SourceEditorFragment>(
            bundle,
            R.style.AppTheme
        )
    }

    @Test
    fun MakeSource_Incorrect_PreservesRest() {
        Espresso.onView(ViewMatchers.withId(R.id.sourcetitletext)).check(matches(isEditTextValueEqualTo(title)))
        Espresso.onView(ViewMatchers.withId(R.id.sourcetitletext)).perform(clearText())
        Espresso.onView(ViewMatchers.withId(R.id.sourcecreatorbutton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.sourcetypes)).perform(
            RecyclerViewActions.scrollToPosition<AttributeViewHolder>(Mana.APEIRON.ordinal)
        )
        Espresso.onView(ViewMatchers.withId(R.id.sourcetypes)).check(
            matches(
                withRecyclerViewText(
                    Mana.APEIRON.ordinal,
                    R.id.manaamounttext,
                    3.toString()
                )
            )
        )
    }

    @Test
    fun MakeSource_Correct_SaysSourceEdited(){
        Espresso.onView(ViewMatchers.withId(R.id.sourcecreatorbutton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.sourcecreatorbutton)).perform(ViewActions.click())
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