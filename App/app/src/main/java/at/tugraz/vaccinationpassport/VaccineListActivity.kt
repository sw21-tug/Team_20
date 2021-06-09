package at.tugraz.vaccinationpassport

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import kotlinx.android.synthetic.main.activity_vaccine_list.*
import java.util.*

class VaccineListActivity : AppCompatActivity() {
    private lateinit var vaccineListAdapter : VaccineListAdapter
    private lateinit var server: Server


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
        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        if (language != null) {
            setLocale(language as String?)
        }
        else {
            setLocale("en")
        }

        vaccineListAdapter = VaccineListAdapter(mutableListOf())

        val tempServer = intent.extras?.get("Server")
        if (tempServer != null) {
            server = tempServer as Server
            server.onProfileReceived = ::setProfileData
            server.onVaccineListReceived = ::setVaccineList
            server.onProfileRequestFailed = { Log.w("Client-Server", "Profile Request failed") }
            server.onVaccineListRequestFailed = { Log.w("Client-Server", "Vaccinelist Request failed") }
            server.getProfile()
            server.getVaccineList()
        } else {
            Log.w("Intent", "The server was not passed to the new Activity")
            server = Server(Repository())
        }

        setContentView(R.layout.activity_vaccine_list)
        rvVaccineList.adapter = vaccineListAdapter;
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

    private fun setVaccineList(vaccineList: List<Vaccination>) {

        vaccineListAdapter.addVaccines(vaccineList)

    }

    fun onSingleVacView(view: View) {
        val intent = Intent(applicationContext, SingleVaccineViewActivity::class.java)
        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        intent.putExtra("Server", server)
        val vaccineName : TextView = view.findViewById<TextView>(R.id.tvVaccineName)
        intent.putExtra("VaccineName", vaccineName.text)
        startActivity(intent)
    }


}