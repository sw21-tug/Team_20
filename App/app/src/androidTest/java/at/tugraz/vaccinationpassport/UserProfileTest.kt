package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserProfileTest
{
    private fun tvtest(id:Int,text:String){
        val scenario = ActivityScenario.launch(UserProfileActivity::class.java)
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
        val text = "Name"

        tvtest(id,text)
    }

    @Test
    fun testUserAgeLabel()
    {
        /*
        * Test if age Label is displayed
         */
        val id = R.id.tvAgeDisplay
        val text = "Age"

        tvtest(id,text)
    }

    @Test
    fun testUserPassNrLabel()
    {
        /*
        * Test if Passport nr Label is displayed
         */
        val id = R.id.tvPassNrDisplay
        val text = "Passport nr."

        tvtest(id,text)
    }

    @Test
    fun testUserNrVacLabel()
    {
        /*
        * Test if NrVacciens Label is displayed
         */
        val id = R.id.tvNrVacDisplay
        val text = "Nr of vacciens"

        tvtest(id,text)
    }

    @Test
    fun testVaccineListPicText()
    {
        /*
        * Test if Vac list picture is displayed
         */
        val scenario = ActivityScenario.launch(UserProfileActivity::class.java)
        onView(withId(R.id.imSeeVacList)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun testProfilePicText()
    {
        /*
        * Test if profile picture is displayed
         */
        val scenario = ActivityScenario.launch(UserProfileActivity::class.java)
        onView(withId(R.id.imPassportImage)).check(matches(isDisplayed()))
        scenario.close()
    }
}