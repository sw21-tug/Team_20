package at.tugraz.vaccinationpassport


import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.drawerlayout.widget.DrawerLayout
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.utils.changeLocale
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val server = Server(Repository())
    lateinit var toggle : ActionBarDrawerToggle

    /*
    fun setLocale(languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = this.intent

        val language = intent.extras?.get(resources.getString(R.string.language_key))
        changeLocale(language as String?, "en", this.resources)

        setContentView(R.layout.activity_main)
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
        val intent = Intent(applicationContext, MainActivity::class.java)
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

    fun onLoginClicked(view: View) {
        val loginDetails = getEnteredData()
        if (loginDetails == null) {
            displayLoginError(getString(R.string.invalid_login_details))
            return
        }
        server.onLoginSuccessful = ::onLoginSuccessful
        server.onLoginFailed = ::onLoginFailed
        server.login(loginDetails)
    }

    fun onRegisterClicked(view: View) {
        displayLoginError(getString(R.string.registering_not_implemented))
    }

    private fun displayLoginError(message: String) {
        val errorView = findViewById<TextView>(R.id.errorMessageView)
        errorView.text = message
        errorView.visibility = View.VISIBLE
    }

    private fun getEnteredData(): LoginDetails? {
        val passport = findViewById<EditText>(R.id.loginPassportNumber) ?: return null
        val passportText = passport.text
        if (passportText.isEmpty() || !passportText.isDigitsOnly()) {
            return null
        }
        val passportNumber = passportText.toString()

        val password = findViewById<EditText>(R.id.loginPassword) ?: return null
        val passwordString = password.text.toString()

        return LoginDetails(passportNumber, passwordString)
    }

    private fun onLoginSuccessful() {
        val password = findViewById<EditText>(R.id.loginPassword)
        password.setText("")

        val language = this.intent.extras?.getString(resources.getString(R.string.language_key))
        // Switch to other activity
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra(applicationContext.resources.getString(R.string.language_key), language)
        startActivity(intent)
    }

    private fun onLoginFailed(couldConnectToServer: Boolean) {
        if (couldConnectToServer) {
            displayLoginError(getString(R.string.invalid_login_details))
        } else {
            displayLoginError(getString(R.string.could_not_connect_to_server))
        }
    }
}