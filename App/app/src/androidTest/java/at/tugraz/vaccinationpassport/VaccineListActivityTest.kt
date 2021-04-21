package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class VaccineListActivityTest
{
    @Test
    fun testRecyclerViewDisplay()
    {
        /*
        * Test if recycler View is displayed
         */
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(R.id.rvVaccineList)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun testVaccineNameText()
    {
        /*
        * Test if Disease text is displayed
         */
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(R.id.tvVaccineName)).check(matches(isDisplayed()))

        onView(withId(R.id.tvVaccineName)).check(matches(withText("Disease")))
        scenario.close()
    }

    @Test
    fun testVaccineDateText()
    {
        /*
        * Test if vaccine date is displayed
         */
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(R.id.tvVaccineDate)).check(matches(isDisplayed()))

        onView(withId(R.id.tvVaccineDate)).check(matches(withText("Date")))
        scenario.close()
    }
}