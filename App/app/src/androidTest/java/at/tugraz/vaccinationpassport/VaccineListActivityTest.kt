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
    private fun tvTest(id:Int, text:String){
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(id)).check(matches(isDisplayed()))

        onView(withId(id)).check(matches(withText(text)))
        scenario.close()
    }
    @Test
    fun testUserNameLabel()
    {
        /*
        * Test if Name Label is displayed
         */
        val id = R.id.tvNameDisplay
        val text = "Name:"

        tvTest(id,text)
    }

    @Test
    fun testUserAgeLabel()
    {
        /*
        * Test if age Label is displayed
         */
        val id = R.id.tvAgeDisplay
        val text = "Age:"

        tvTest(id,text)
    }

    @Test
    fun testUserPassNrLabel()
    {
        /*
        * Test if Passport nr Label is displayed
         */
        val id = R.id.tvPassNrDisplay
        val text = "Passport Nr:"

        tvTest(id,text)
    }

    @Test
    fun testUserNrVacLabel()
    {
        /*
        * Test if NrVacciens Label is displayed
         */
        val id = R.id.tvNrVacDisplay
        val text = "Nr of Vaccines:"

        tvTest(id,text)
    }

    @Test
    fun testProfilePicText()
    {
        /*
        * Test if profile picture is displayed
         */
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(R.id.imPassportImage)).check(matches(isDisplayed()))
        scenario.close()
    }

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
        onView(withId(R.id.tvDisease)).check(matches(isDisplayed()))

        onView(withId(R.id.tvDisease)).check(matches(withText("Disease")))
        scenario.close()
    }

    @Test
    fun testVaccineDateText()
    {
        /*
        * Test if vaccine date is displayed
         */
        val scenario = ActivityScenario.launch(VaccineListActivity::class.java)
        onView(withId(R.id.tvVaccinationDate)).check(matches(isDisplayed()))

        onView(withId(R.id.tvVaccinationDate)).check(matches(withText("Date")))
        scenario.close()
    }
}