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
    private fun tvTest(id:Int, text:String){
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
    fun testQrDescription()
    {
        /*
         * Test if the description of the QR code picture is displayed
         */
        val id = R.id.tvInfoQr
        val text = "Scan QR Code"

        tvTest(id, text)
    }

    @Test
    fun testQrPic()
    {
        /*
         * Test if the QR code picture is displayed
         */
        val scenario = ActivityScenario.launch(UserProfileActivity::class.java)
        onView(withId(R.id.imScanQr)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun testVaccineListDescription()
    {
        /*
         * Test if the description of the vaccine list picture is displayed
         */
        val id = R.id.tvInfoList
        val text = "See my Vaccines"

        tvTest(id, text)
    }

    @Test
    fun testVaccineListPicText()
    {
        /*
         * Test if the vaccine list picture is displayed
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