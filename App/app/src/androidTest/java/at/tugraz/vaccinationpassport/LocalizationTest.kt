package at.tugraz.vaccinationpassport

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class LocalizationTest
{
    private fun tvtestLocale(language: String, id:Int,text:String) {

        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(appContext, UserProfileActivity::class.java)
        intent.putExtra(appContext.resources.getString(R.string.language_key), language)
        val scenario = ActivityScenario.launch<UserProfileActivity>(intent)

        onView(withId(id)).check(matches(withText(text)))
        scenario.close()
    }

    @Test
    fun testUserNameLabelEN()
    {
        /*
        * Test if Name Label is displayed in EN
         */

        val id = R.id.tvNameDisplay
        val text = "Name"
        tvtestLocale("en", id, text)
    }

    @Test
    fun testUserAgeLabelEN()
    {
        /*
        * Test if age Label is displayed
         */
        val id = R.id.tvAgeDisplay
        val text = "Age"
        tvtestLocale("en", id, text)
    }

    @Test
    fun testUserPassNrLabelEN()
    {
        /*
        * Test if Passport nr Label is displayed
         */
        val id = R.id.tvPassNrDisplay
        val text = "Passport nr."
        tvtestLocale("en", id, text)
    }

    @Test
    fun testUserNrVacLabelEN()
    {
        /*
        * Test if NrVacciens Label is displayed
         */
        val id = R.id.tvNrVacDisplay
        val text = "Nr of vacciens"
        tvtestLocale("en", id, text)
    }

    @Test
    fun testUserNameLabelRU()
    {
        /*
        * Test if Name Label is displayed in EN
         */

        val id = R.id.tvNameDisplay
        val text = "Имя"
        tvtestLocale("ru", id, text)
    }

    @Test
    fun testUserAgeLabelRU()
    {
        /*
        * Test if age Label is displayed
         */
        val id = R.id.tvAgeDisplay
        val text = "Возраст"
        tvtestLocale("ru", id, text)
    }

    @Test
    fun testUserPassNrLabelRU()
    {
        /*
        * Test if Passport nr Label is displayed
         */
        val id = R.id.tvPassNrDisplay
        val text = "Паспорт №"
        tvtestLocale("ru", id, text)
    }

    @Test
    fun testUserNrVacLabelRU()
    {
        /*
        * Test if NrVacciens Label is displayed
         */
        val id = R.id.tvNrVacDisplay
        val text = "Kol-vo vaktsin"
        tvtestLocale("ru", id, text)
    }
}