package at.tugraz.vaccinationpassport


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onRegisterClicked(view: View) {
        displayLoginError("Registering is not Implemented")
    }

    private fun displayLoginError(message: String) {
        val errorView = findViewById<TextView>(R.id.errorMessageView)
        errorView.text = message
        errorView.visibility = View.VISIBLE
    }
}