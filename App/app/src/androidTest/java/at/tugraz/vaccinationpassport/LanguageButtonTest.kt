package at.tugraz.vaccinationpassport

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LanguageButtonTest {

    @Test
    fun testChangeLanguageToRU()
    {
        /*
        * Test if language button click changes language to ru
         */

        // launch main activity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        val appContext = ApplicationProvider.getApplicationContext<Context>()

        //check en language
        Espresso.onView(withId(R.id.loginTitle)).check(matches(withText("Login")))
        Espresso.onView(withId(R.id.loginButton)).check(matches(withText("Login")))
        Espresso.onView(withId(R.id.textView)).check(matches(withText("Don\'t have an account?")))
        Espresso.onView(withId(R.id.SignUpButton)).check(matches(withText("Sign up")))

        // open nav drawer
        Espresso.onView(withContentDescription(at.tugraz.vaccinationpassport.R.string.drawer_open)
        ).perform(click())

        // check if nav header is displayed
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.nav_layout))
            .check(matches(isDisplayed()))

        // check if language button is clickable
        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.btn_language))
            .check(matches(isClickable()))

        Espresso.onView(withId(at.tugraz.vaccinationpassport.R.id.btn_language))
            .perform(click())

        //check ru language
        Espresso.onView(withId(R.id.loginTitle)).check(matches(withText("Авторизоваться")))
        Espresso.onView(withId(R.id.loginButton)).check(matches(withText("Авторизоваться")))
        Espresso.onView(withId(R.id.textView)).check(matches(withText("Нет учетной записи?")))
        Espresso.onView(withId(R.id.SignUpButton)).check(matches(withText("Зарегистрируйтесь")))
    }

}