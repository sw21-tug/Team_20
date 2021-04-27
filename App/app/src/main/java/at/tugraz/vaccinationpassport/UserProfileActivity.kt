package at.tugraz.vaccinationpassport

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val nameText : TextView =  findViewById<TextView>(R.id.tvNameText) //findViewById(R.id.tvNameText) as TextView
        val ageText : TextView = findViewById<TextView>(R.id.tvAgeText)
        val passNrText : TextView = findViewById<TextView>(R.id.tvPassNrText)
        val nrVacText : TextView = findViewById<TextView>(R.id.tvNrVacText)

        val person = listOf<String>("Max Mustermann","25","1234567","5")

        nameText.text = person[0]
        ageText.text = person[1]
        passNrText.text = person[2]
        nrVacText.text = person[3]
    }

    fun showingVacList(view: View) {
        val intent = Intent(this, VaccineListActivity::class.java)
        startActivity(intent)
    }
}