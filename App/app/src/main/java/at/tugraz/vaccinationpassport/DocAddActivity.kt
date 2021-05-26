package at.tugraz.vaccinationpassport

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_doctor_adds_vaccines.*
import java.util.*

class DocAddActivity : AppCompatActivity() {

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
        } else {
            setLocale("en")
        }

        setContentView(R.layout.activity_doctor_adds_vaccines)
    }
}