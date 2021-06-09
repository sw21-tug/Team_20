package at.tugraz.vaccinationpassport

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.drawerlayout.widget.DrawerLayout
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
import at.tugraz.vaccinationpassport.utils.changeLocale
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_doctor_adds_vaccines.*
import kotlinx.android.synthetic.main.activity_doctor_adds_vaccines.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DocAddActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var server: Server
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = this.intent.extras?.get(resources.getString(R.string.language_key))
        changeLocale(language as String?, "en", this.resources)

        setServer()

        setContentView(R.layout.activity_doctor_adds_vaccines)
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

    private fun setServer() {
        val tempServer = intent.extras?.get("Server")
        server = if (tempServer != null) {
            tempServer as Server
        } else {
            Log.w("Intent", "The server was not passed to the new Activity")
            Server(Repository())
        }
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
        val intent = Intent(applicationContext, DocAddActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    fun onAddClicked(view: View) {

        val vacDetails = getEnteredData()
        if (vacDetails == null) {
            //displayAddVaccineError(getString(R.string.invalid_login_details))
            return
        }

        server.onVaccineAdded = ::onVaccineAdd
        server.onVaccineAddingFailed = ::onVaccineAddingFailed
        server.addVaccine(vacDetails)
    }

    private fun getEnteredData(): VaccineDetails? {
        val passport = findViewById<EditText>(R.id.PassportNumber) ?: return null
        val name = findViewById<EditText>(R.id.vaccinesName) ?: return null
        val date = findViewById<EditText>(R.id.vaccinesDate) ?: return null
        val time = findViewById<EditText>(R.id.vaccinesTime) ?: return null

        if(!passport.text.isDigitsOnly() || name.text.isEmpty() || date.text.isEmpty())
            return null;

        return VaccineDetails(passport.text.toString(),
            Vaccination(
                name.text.toString(),
                date.text.toString() + " " + time.text.toString()))
    }

    private fun onVaccineAdd() {
        val passport = findViewById<EditText>(R.id.PassportNumber) ?: return
        val name = findViewById<EditText>(R.id.vaccinesName) ?: return
        val date = findViewById<EditText>(R.id.vaccinesDate) ?: return
        val time = findViewById<EditText>(R.id.vaccinesTime) ?: return

        passport.setText("")
        name.setText("")
        date.setText("")
        time.setText("")
    }

    private fun onVaccineAddingFailed() {
        //displayAddVaccineError(getString(R.string.invalid_login_details))
    }

}