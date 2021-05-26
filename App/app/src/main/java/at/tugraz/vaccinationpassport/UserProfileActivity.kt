package at.tugraz.vaccinationpassport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import at.tugraz.vaccinationpassport.utils.changeLocale


class UserProfileActivity : AppCompatActivity() {

    private lateinit var server: Server


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = this.intent.extras?.get(resources.getString(R.string.language_key))
        changeLocale(language as String?, "en", this.resources)

        val tempServer = intent.extras?.get("Server")
        if (tempServer != null) {
            server = tempServer as Server
            server.onProfileReceived = ::setProfileData
            server.onProfileRequestFailed = { Log.w("Client-Server", "Profile Request failed") }
            server.getProfile()
        } else {
            Log.w("Intent", "The server was not passed to the new Activity")
            server = Server(Repository())
        }

        setContentView(R.layout.activity_user_profile)
    }

    fun showingVacList(view: View) {
        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        val intent = Intent(this, VaccineListActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
    }

    private fun setProfileData(profile: ProfileData) {
        val nameText : TextView =  findViewById<TextView>(R.id.tvNameText)
        val ageText : TextView = findViewById<TextView>(R.id.tvAgeText)
        val passNrText : TextView = findViewById<TextView>(R.id.tvPassNrText)
        val nrVacText : TextView = findViewById<TextView>(R.id.tvNrVacText)

        nameText.text = profile.name
        ageText.text = profile.age.toString()
        passNrText.text = profile.passportNumber.toString()
        nrVacText.text = profile.nrOfVaccines.toString()
    }
}