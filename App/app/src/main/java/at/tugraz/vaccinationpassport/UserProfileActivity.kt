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
        val intent = this.intent
        val language = intent.extras?.get(resources.getString(R.string.language_key))
        if (language != null) {
            setLocale(language as String?)
        }
        else {
            setLocale("en")
        }

        setContentView(R.layout.activity_user_profile)

        val nameText : TextView =  findViewById<TextView>(R.id.tvNameText) //findViewById(R.id.tvNameText) as TextView
        val ageText : TextView = findViewById<TextView>(R.id.tvAgeText)
        val passNrText : TextView = findViewById<TextView>(R.id.tvPassNrText)
        val nrVacText : TextView = findViewById<TextView>(R.id.tvNrVacText)

        val person = listOf<String>("Max Mustermann", "25", "1234567", "5")

        nameText.text = person[0]
        ageText.text = person[1]
        passNrText.text = person[2]
        nrVacText.text = person[3]
    }

    fun showingVacList(view: View) {
        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        val intent = Intent(this, VaccineListActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
    }
}