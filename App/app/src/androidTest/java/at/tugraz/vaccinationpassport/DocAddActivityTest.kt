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
class DocAddActivityTest{

    @Test
    fun test_activity_inView() {
        val activityScenario = ActivityScenario.launch(DocAddActivity::class.java)

        onView(withId(R.id.vacImage)).check(matches(isDisplayed()))
        onView(withId(R.id.addVacTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.PassportNumber)).check(matches(isDisplayed()))
        onView(withId(R.id.vaccinesName)).check(matches(isDisplayed()))
        onView(withId(R.id.vaccinesDate)).check(matches(isDisplayed()))
        onView(withId(R.id.vaccineTime)).check(matches(isDisplayed()))

        onView(withId(R.id.addVacButton)).check(matches(isDisplayed()));

        activityScenario.close()
    }

    @Test
    fun AddVacButtonIsClickable() {
        val activityScenario = ActivityScenario.launch(DocAddActivity::class.java)
        onView(withId(R.id.addVacButton)).check(matches(isClickable()));
        activityScenario.close()
    }
}

