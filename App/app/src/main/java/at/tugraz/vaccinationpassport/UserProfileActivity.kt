package at.tugraz.vaccinationpassport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val nameText : TextView = findViewById(R.id.tvNameText) as TextView
        val ageText : TextView = findViewById(R.id.tvAgeText) as TextView
        val passNrText : TextView = findViewById(R.id.tvPassNrText) as TextView
        val nrVacText : TextView = findViewById(R.id.tvNrVacText) as TextView

        val person = listOf<String>("Max Mustermann","25","1234567","5")

        nameText.text = person[0]
        ageText.text = person[1]
        passNrText.text = person[2]
        nrVacText.text = person[3]
    }
}