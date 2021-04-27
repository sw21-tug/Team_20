package at.tugraz.vaccinationpassport


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails

class MainActivity : AppCompatActivity() {
    private val server = Server(Repository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLoginClicked(view: View) {
        val loginDetails = getEnteredData()
        if (loginDetails == null) {
            displayLoginError("Invalid Passport Number / Password")
            return
        }
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
}