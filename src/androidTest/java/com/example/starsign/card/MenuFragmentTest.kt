package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.starsign.R
import com.example.starsign.menu.MenuFragment
import com.example.starsign.menu.MenuFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MenuFragmentTest {
    @Test
    fun clickCreator_navigateToCardCreator(){
        val navController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<MenuFragment>(Bundle(),
                R.style.AppTheme
            )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.cardcreatorbutton)).perform(click())
        verify(navController).navigate(
            MenuFragmentDirections.actionMenuFragmentToCardCreatorBox()
        )
    }
}