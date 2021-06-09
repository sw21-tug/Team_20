package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
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
        onView(withId(R.id.vaccinesTime)).check(matches(isDisplayed()))

        onView(withId(R.id.addVacButton)).check(matches(isDisplayed()));

        activityScenario.close()
    }

    @Test
    fun AddVacButtonIsClickable() {
        val activityScenario = ActivityScenario.launch(DocAddActivity::class.java)
        onView(withId(R.id.addVacButton)).check(matches(isClickable()));
        activityScenario.close()
    }

    @Test
    fun onlyAddVaccineTest_Successful() {
        val activityScenarioDoctor = ActivityScenario.launch(MainActivity::class.java)
        loginDoctor()

        val vaccineDetails = VaccineDetails("12345678", Vaccination("MyCold", "10.10.2020"))

        onView(withId(R.id.PassportNumber)).perform(
            typeText(vaccineDetails.passportNr),
            closeSoftKeyboard()
        )
        onView(withId(R.id.vaccinesName)).perform(
            typeText(vaccineDetails.vaccine.name),
            closeSoftKeyboard()
        )
        onView(withId(R.id.vaccinesDate)).perform(
            typeText(vaccineDetails.vaccine.date),
            closeSoftKeyboard()
        )
        onView(withId(R.id.addVacButton)).perform(click())


        onView(withId(R.id.PassportNumber)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesName)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesDate)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesTime)).check(matches(isDisplayed()))
            .check(matches(withText("")))

        activityScenarioDoctor.close()
    }

    private fun loginDoctor() {
        onView(withId(R.id.loginPassportNumber)).perform(typeText("11223344"), closeSoftKeyboard())
        onView(withId(R.id.loginPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        sleep(1000)
    }
}

