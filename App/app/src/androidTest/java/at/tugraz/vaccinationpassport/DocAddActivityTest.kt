package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
import it.xabaras.android.espresso.recyclerviewchildactions.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
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
        val vaccineDetails = VaccineDetails("12345678", Vaccination("MyCold", "10.10.2020"))

        addVaccineAsDoctor(vaccineDetails)
    }

    @Test
    fun addVaccineTest_Successful() {
        val vaccineDetails = VaccineDetails("12345678", Vaccination("MyCold", "10.10.2020"))

        addVaccineAsDoctor(vaccineDetails)


        ensureVaccineWasAdded(vaccineDetails)
    }

    private fun addVaccineAsDoctor(vaccineDetails: VaccineDetails) {
        val activityScenarioDoctor = ActivityScenario.launch(MainActivity::class.java)
        loginDoctor()

        addVaccine(vaccineDetails)
        ensureEmptyVaccineFields()

        activityScenarioDoctor.close()
    }

    private fun ensureVaccineWasAdded(vaccineDetails: VaccineDetails) {
        val activityScenarioUser = ActivityScenario.launch(MainActivity::class.java)
        loginUser()

        openVaccineList()
        ensureVaccineListContains(2, vaccineDetails.vaccine)

        activityScenarioUser.close()
    }

    private fun ensureVaccineListContains(position: Int, vaccine: Vaccination) {
        onView(withId(R.id.rvVaccineList))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.tvVaccineName,
                position, withText(vaccine.name))))
    }

    private fun openVaccineList() {
        onView(withId(R.id.tvInfoList)).perform(click())
    }

    private fun loginDoctor() {
        login(LoginDetails("11223344", "password"))
    }

    private fun loginUser() {
        login(LoginDetails("12345678", "password"))
    }

    private fun login(loginDetails: LoginDetails) {
        onView(withId(R.id.loginPassportNumber)).perform(typeText(loginDetails.passportNumber), closeSoftKeyboard())
        onView(withId(R.id.loginPassword)).perform(typeText(loginDetails.password), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        sleep(1000)
    }

    private fun addVaccine(vaccineDetails: VaccineDetails) {
        onView(withId(R.id.PassportNumber)).perform(
            typeText(vaccineDetails.passportNumber),
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

        sleep(1000)
    }

    private fun ensureEmptyVaccineFields() {
        onView(withId(R.id.PassportNumber)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesName)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesDate)).check(matches(isDisplayed()))
            .check(matches(withText("")))
        onView(withId(R.id.vaccinesTime)).check(matches(isDisplayed()))
            .check(matches(withText("")))
    }
}

