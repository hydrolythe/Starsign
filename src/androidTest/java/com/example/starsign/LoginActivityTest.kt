package com.example.starsign

import android.app.Activity
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.starsign.menu.MenuFragment
import com.example.starsign.ui.login.LoginActivity
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest : KoinTest{

    private lateinit var scenario: ActivityScenario<LoginActivity>
    @Before
    fun init(){
        stopKoin()
        startKoin{modules(fakeUserModule)}
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun LoginActivity_login_CorrectParameters_SwitchesScreens(){
        onView(withId(R.id.username)).perform(replaceText("Nedl"))
        onView(withId(R.id.password)).perform(replaceText("Globoesporte"))
        onView(withId(R.id.login)).perform(click())
        assertThat(Activity.RESULT_OK, `is`(scenario.result.resultCode))
    }

    @Test
    fun LoginActivity_login_FalseParameters_ShowsErrorWarning(){
        onView(withId(R.id.username)).perform(replaceText("Valsegebruiker"))
        onView(withId(R.id.password)).perform(replaceText("Valswachtwoord"))
        onView(withId(R.id.login)).perform(click())
        onView(withId(R.id.errortext)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun LoginActivity_registration_RedirectsToRegister(){
        onView(withId(R.id.registerbutton)).perform(click())
        assertThat(Lifecycle.State.DESTROYED, `is`(scenario.state))
    }
}