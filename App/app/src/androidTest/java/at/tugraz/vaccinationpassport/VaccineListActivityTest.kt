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
    private fun checkLabel(id:Int, text:String){
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
        onView(withId(R.id.rvVaccineList)).check(matches(isDisplayed()))

        scenario.close()
    }
}