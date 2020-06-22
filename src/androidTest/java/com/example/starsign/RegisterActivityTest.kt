package com.example.starsign

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starsign.register.RegisterActivity
import com.example.starsign.ui.login.LoginActivity
import org.hamcrest.core.Is
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest : KoinTest {
    @Test
    fun RegisterActivity_register_CorrectParameters_SwitchesScreens(){
        stopKoin()
        startKoin{modules(fakeUserModule)}
        val scenario = launchActivity<RegisterActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        Espresso.onView(ViewMatchers.withId(R.id.usernamefield)).perform(ViewActions.replaceText("abc"))
        Espresso.onView(ViewMatchers.withId(R.id.passwordfield))
            .perform(ViewActions.replaceText("NedZuckerman23"))
        Espresso.onView(ViewMatchers.withId(R.id.repeatpasswordfield))
            .perform(ViewActions.replaceText("NedZuckerman23"))
        Espresso.onView(ViewMatchers.withId(R.id.registratebutton)).perform(ViewActions.click())
        ViewMatchers.assertThat(Activity.RESULT_OK, Is.`is`(scenario.result.resultCode))
    }

    @Test
    fun RegisterActivity_register_FalseParameters_ShowsErrorWarning(){
        stopKoin()
        startKoin{modules(fakeUserModule)}
        val scenario = launchActivity<RegisterActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        Espresso.onView(ViewMatchers.withId(R.id.usernamefield))
            .perform(ViewActions.replaceText("Valsegebruiker"))
        Espresso.onView(ViewMatchers.withId(R.id.passwordfield))
            .perform(ViewActions.replaceText("Valswachtwoord"))
        Espresso.onView(ViewMatchers.withId(R.id.registratebutton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.errortext))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}