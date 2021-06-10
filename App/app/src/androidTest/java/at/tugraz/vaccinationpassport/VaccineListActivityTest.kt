package at.tugraz.vaccinationpassport

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.xabaras.android.espresso.recyclerviewchildactions.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VaccineListActivityTest
{
    private fun checkLabel(id: Int, text: String){
        onView(withId(id)).check(matches(isDisplayed()))
        onView(withId(id)).check(matches(withText(text)))
    }
    @Test
    fun testVaccineListLabels()
    {
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        checkLabel(R.id.tvNameDisplay, "Name:")
        checkLabel(R.id.tvAgeDisplay, "Age:")
        checkLabel(R.id.tvPassNrDisplay, "Passport Nr:")
        checkLabel(R.id.tvNrVacDisplay, "Nr of Vaccines:")
        checkLabel(R.id.tvVaccinationDate, "Date")
        checkLabel(R.id.tvDisease, "Disease")
        onView(withId(R.id.imPassportImage)).check(matches(isDisplayed()))
        scenario.close()
    }
    @Test
    fun testVaccineListRealData()
    {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.loginPassportNumber)).perform(
            ViewActions.typeText("12345678"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.loginPassword)).perform(
            ViewActions.typeText("password"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.loginButton)).perform(ViewActions.click())
        Thread.sleep(1000)
        onView(withId(R.id.tvInfoList)).perform(ViewActions.click())
        Thread.sleep(250)
        checkLabel(R.id.tvNameText, "Max Mustermann")
        checkLabel(R.id.tvAgeText, "30")
        checkLabel(R.id.tvPassNrText, "12345678")
        //checkLabel(R.id.tvNrVacText, "4")

        onView(withId(R.id.rvVaccineList))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.tvVaccineName,
                0, withText("Covid"))))

        onView(withId(R.id.rvVaccineList))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.tvVaccineName,
                1, withText("Malaria"))))

        onView(withId(R.id.rvVaccineList))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.tvVaccineDate,
                0, withText("05-05-2021"))))

        onView(withId(R.id.rvVaccineList))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.tvVaccineDate,
                1, withText("05-05-2021"))))


        onView(withId(R.id.rvVaccineList))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()));

        Thread.sleep(500)
        checkLabel(R.id.tvVacName, "Covid")

        scenario.close()
    }
}