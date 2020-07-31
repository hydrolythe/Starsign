package com.example.starsign.card

import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import com.example.starsign.cardformulars.MonsterCreatorFragmentDirections
import com.example.starsign.database.Mana
import com.example.starsign.database.ProtoMonster
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
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MonsterCreatorTest {

    private lateinit var navController: NavController
    private lateinit var scenario: FragmentScenario<MonsterCreatorFragment>


    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<MonsterCreatorFragment>(Bundle(),
            R.style.AppTheme
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun createMonster_CorrectMonster_transfersInfoToNextFragment(){
        val s = "Herculitos"
        onView(withId(R.id.titletextfield)).perform(replaceText(s))
        val i = 10.toString()
        onView(withId(R.id.lifetextfield)).perform(replaceText(i))
        val i1 = 5.toString()
        onView(withId(R.id.attacktextfield)).perform(replaceText(i1))
        val i2 = 3.toString()
        onView(withId(R.id.defensetextfield)).perform(replaceText(i2))
        val i3 = 2.toString()
        onView(withId(R.id.magicattacktextfield)).perform(replaceText(i3))
        val i4 = 3.toString()
        onView(withId(R.id.magicdefensefield)).perform(replaceText(i4))
        val i5 = 2.toString()
        onView(withId(R.id.mptextfield)).perform(replaceText(i5))
        onView(withId(R.id.submitmonster)).perform(click())
        Mockito.verify(navController).navigate(MonsterCreatorFragmentDirections.actionMonsterCreatorFragmentToTrueMonsterCreatorFragment(
            ProtoMonster(s, Integer.parseInt(i), Integer.parseInt(i1), Integer.parseInt(i2), Integer.parseInt(i3), Integer.parseInt(i4), Integer.parseInt(i5))
        ))
    }
}