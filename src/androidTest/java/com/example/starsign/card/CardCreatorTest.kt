package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.starsign.R
import com.example.starsign.cardcreator.CardCreatorAdapter
import com.example.starsign.cardcreator.CardCreatorFragment
import com.example.starsign.cardcreator.CardCreatorFragmentDirections
import com.example.starsign.database.Magic
import com.example.starsign.database.Monster
import com.example.starsign.database.Source
import com.example.starsign.fakeCardModule
import com.example.starsign.listDbCards
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.verify


@ExperimentalCoroutinesApi
@MediumTest
class CardCreatorTest : KoinTest {
    private lateinit var scenario: FragmentScenario<CardCreatorFragment>
    private lateinit var navController : NavController

    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeCardModule)}
        navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<CardCreatorFragment>(Bundle(),
            R.style.AppTheme
        )
        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun CreatorButton_onClick_toCreateCardMenu(){
        onView(withId(R.id.createcardButton)).perform(click())
        verify(navController).navigate(
            CardCreatorFragmentDirections.actionCardCreatorBoxToTypeOfCardFragment()
        )
    }

    @Test
    fun DeleteButton_onClick_toDeleteCardMenu(){
        onView(withId(R.id.deletecardsbutton)).perform(click())
        verify(navController).navigate(
            CardCreatorFragmentDirections.actionCardCreatorBoxToCarddeleteFragment()
        )
    }

    @Test
    fun Details_onClick_givesDetailsCard(){
        val selected = listDbCards[0].asDomainModel()
        onView(withId(R.id.cardlist)).perform(RecyclerViewActions.actionOnItemAtPosition<CardCreatorAdapter.ViewHolder>(0, click()))
        when(selected){
            is Monster -> verify(navController).navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToMonsterDetailFragment(selected))
            is Magic -> verify(navController).navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToSpellDetailFragment(selected))
            is Source -> verify(navController).navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToSourceDetailFragment(selected))
        }
    }
}