package com.example.starsign.card

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.starsign.R
import com.example.starsign.typeofcardmenu.TypeOfCardFragment
import com.example.starsign.typeofcardmenu.TypeOfCardFragmentDirections
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TypeofcardmenuTest {
    private lateinit var navController: NavController
    @Before
    fun before(){
        navController = Mockito.mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<TypeOfCardFragment>(Bundle(),
                R.style.AppTheme
            )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun clickMonster_navigateToMonsterCreator(){

        Espresso.onView(ViewMatchers.withId(R.id.monstercreatorbutton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            TypeOfCardFragmentDirections.actionTypeOfCardFragmentToMonsterCreatorFragment()
        )
    }
    @Test
    fun clickSpell_navigateToSpellCreator(){
        val navController = Mockito.mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<TypeOfCardFragment>(Bundle(),
                R.style.AppTheme
            )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.spellcreatorbutton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            TypeOfCardFragmentDirections.actionTypeOfCardFragmentToSpellCreator()
        )
    }
    @Test
    fun clickSource_navigateToSourceCreator(){
        val navController = Mockito.mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<TypeOfCardFragment>(Bundle(),
                R.style.AppTheme
            )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.sourcecreatorbutton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            TypeOfCardFragmentDirections.actionTypeOfCardFragmentToSourceCreator()
        )
    }
}