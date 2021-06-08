package at.tugraz.vaccinationpassport

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class VaccineListActivityTest
{
    private fun checkLabel(id: Int, text: String){
        onView(withId(id)).check(matches(isDisplayed()))
        onView(withId(id)).check(matches(withText(text)))
    }

    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
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

        onView(withId(R.id.rvVaccineList)).check(matches(withViewAtPosition(0,
            hasDescendant(allOf(withId(R.id.tvVaccineName), withText("Covid"))))))

        onView(withId(R.id.rvVaccineList)).check(matches(withViewAtPosition(0,
            hasDescendant(allOf(withId(R.id.tvVaccinationDate), withText("21-01-2021"))))))

        onView(withId(R.id.rvVaccineList)).check(matches(withViewAtPosition(0,
            hasDescendant(allOf(withId(R.id.tvVaccineName), withText("FSME"))))))

        onView(withId(R.id.rvVaccineList)).check(matches(withViewAtPosition(0,
            hasDescendant(allOf(withId(R.id.tvVaccinationDate), withText("29-05-2015"))))))



        scenario.close()
    }
}