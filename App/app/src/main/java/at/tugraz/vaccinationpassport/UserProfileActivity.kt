package at.tugraz.vaccinationpassport

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class UserProfileActivity : AppCompatActivity() {

    fun setLocale(languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = this.intent.extras?.get(resources.getString(R.string.language_key))
        if (language != null) {
            setLocale(language as String?)
        }
        else {
            setLocale("en")
        }

        setContentView(R.layout.activity_user_profile)

        findViewById<TextView>(R.id.tvNameText).text = "Max Mustermann"
        findViewById<TextView>(R.id.tvAgeText).text = "25"
        findViewById<TextView>(R.id.tvPassNrText).text = "1234567"
        findViewById<TextView>(R.id.tvNrVacText).text = "5"
    }

    fun showingVacList(view: View) {
        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        val intent = Intent(this, VaccineListActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
    }

}