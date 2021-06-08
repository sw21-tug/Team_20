package at.tugraz.vaccinationpassport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import at.tugraz.vaccinationpassport.utils.changeLocale
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class UserProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var server: Server
    lateinit var toggle : ActionBarDrawerToggle

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

        val drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        // set the toolbar as action bar
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)

        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_language_ru) {
            changeLanguage("ru")
        }
        if (item.itemId == R.id.btn_language_en) {
            changeLanguage("en")
        }
        return true
    }

    private fun changeLanguage(language: String) {
        val intent = Intent(applicationContext, UserProfileActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        intent.putExtra("Server", server)
        startActivity(intent)
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