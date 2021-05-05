package at.tugraz.vaccinationpassport

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HamburgerMenuTest {

    @Test
    fun testOpenCloseDrawerNavigation()
    {
        /*
        * Test if nav drawer opens and closes
         */

        // launch main activity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // open nav drawer
        Espresso.onView(withContentDescription(at.tugraz.vaccinationpassport.R.string.drawer_open)
        ).perform(click())

        // check if nav header is displayed
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.nav_layout))
            .check(matches(isDisplayed()))

        // check if about button is clickable
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.btn_about_page))
            .check(matches(isClickable()))

        // check if language button is clickable
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.btn_language))
            .check(matches(isClickable()))

        // close drawer
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.loginPassportNumber)
        ).perform(click())

        // check if drawer is gone
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.nav_layout))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }
}