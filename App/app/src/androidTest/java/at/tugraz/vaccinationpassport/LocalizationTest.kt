package at.tugraz.vaccinationpassport

import android.content.res.Configuration
import android.content.res.Resources
import androidx.test.core.app.ActivityScenario
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
    private fun tvtestLocale(language: String, id:Int,text:String){
        setLocale(language)

        val scenario = ActivityScenario.launch(UserProfileActivity::class.java)
        onView(withId(id)).check(matches(isDisplayed()))

        onView(withId(id)).check(matches(withText(text)))
        scenario.close()
    }

    private fun setLocale(language: String, country: String = "default") {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val baseContext = InstrumentationRegistry.getInstrumentation().context
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
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