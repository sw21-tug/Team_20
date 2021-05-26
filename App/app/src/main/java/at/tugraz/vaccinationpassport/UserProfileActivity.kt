package at.tugraz.vaccinationpassport

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.tugraz.vaccinationpassport.utils.changeLocale
import java.util.*


class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = this.intent.extras?.get(resources.getString(R.string.language_key))
        changeLocale(language as String?, "en", this.resources)

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