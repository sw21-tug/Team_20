package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test

import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Test
    fun test_activity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.main)).check(matches(isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun LoginButtonIsDisplayed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        activityScenario.close()
    }

    @Test
    fun LoginButtonIsClickable() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.loginButton)).check(matches(isClickable()));
        activityScenario.close()
    }

    @Test
    fun SignUpButtonIsDisplayed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.SignUpButton)).check(matches(isDisplayed()));
        activityScenario.close()
    }

    @Test
    fun SignUpButtonIsClickable() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.SignUpButton)).check(matches(isClickable()));
        activityScenario.close()
    }

    @Test
    fun LoginIsSuccesfull() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.loginPassportNumber)).perform(typeText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.loginPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        sleep(1000)

        onView(withId(R.id.VaccineList)).check(matches(isDisplayed()));
        activityScenario.close()
    }

    @Test
    fun LoginFailed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.errorMessageView)).check(matches(isDisplayed()))
            .check(matches(withText("Invalid Passport Number / Password")));
        activityScenario.close()
    }

    @Test
    fun SignUpPage() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.SignUpButton)).perform(click())

        onView(withId(R.id.errorMessageView)).check(matches(isDisplayed()))
            .check(matches(withText("Registering is not Implemented")));
        activityScenario.close()
    }
}

