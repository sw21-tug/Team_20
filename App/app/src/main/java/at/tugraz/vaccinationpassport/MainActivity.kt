package at.tugraz.vaccinationpassport


import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val server = Server(Repository())
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            displayLoginError("Invalid Passport Number / Password")
            return
        }
        server.onLoginSuccessful = ::onLoginSuccessful
        server.onLoginFailed = ::onLoginFailed
        server.login(loginDetails)
    }

    fun onRegisterClicked(view: View) {
        displayLoginError("Registering is not Implemented")
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

        // Switch to other activity
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }

    private fun onLoginFailed(couldConnectToServer: Boolean) {
        if (couldConnectToServer) {
            displayLoginError("Invalid Passport Number / Password")
        } else {
            displayLoginError("Could not connect to the server")
        }
    }
}