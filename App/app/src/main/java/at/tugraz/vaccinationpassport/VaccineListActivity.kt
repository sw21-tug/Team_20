package at.tugraz.vaccinationpassport

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_vaccine_list.*
import kotlinx.android.synthetic.main.activity_vaccine_list.toolbar
import java.util.*

class VaccineListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var vaccineListAdapter : VaccineListAdapter
    lateinit var toggle : ActionBarDrawerToggle

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

        setContentView(R.layout.activity_vaccine_list)

        val nameText : TextView =  findViewById<TextView>(R.id.tvNameText) //findViewById(R.id.tvNameText) as TextView
        val ageText : TextView = findViewById<TextView>(R.id.tvAgeText)
        val passNrText : TextView = findViewById<TextView>(R.id.tvPassNrText)
        val nrVacText : TextView = findViewById<TextView>(R.id.tvNrVacText)

        val person = listOf<String>("Max Mustermann", "25", "1234567", "5")

        nameText.text = person[0]
        ageText.text = person[1]
        passNrText.text = person[2]
        nrVacText.text = person[3]

        vaccineListAdapter = VaccineListAdapter(mutableListOf())
        vaccineListAdapter.addVaccine(Vaccination("Covid", "05-05-2021"))
        vaccineListAdapter.addVaccine(Vaccination("Malaria", "05-05-2021"))
        rvVaccineList.adapter = vaccineListAdapter;

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

    private fun changeLanguage(language: String) {
        val intent = Intent(applicationContext, VaccineListActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
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
}