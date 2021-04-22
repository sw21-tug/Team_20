package at.tugraz.vaccinationpassport

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class UserProfilActivity : AppCompatActivity() {
    //private Button login;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profil)

        val nameText : TextView = findViewById(R.id.nameText) as TextView
        val ageText : TextView = findViewById(R.id.ageText) as TextView
        val passNrText : TextView = findViewById(R.id.passNrText) as TextView
        val nrVacText : TextView = findViewById(R.id.nrVacText) as TextView

        val person = listOf<String>("Max Mustermann","25","1234567","5")

        nameText.text = person[0]
        ageText.text = person[1]
        passNrText.text = person[2]
        nrVacText.text = person[3]

        /*val button = findViewById<Button>(R.id.loginButton)
        button.setOnClickListener{
            val intent = Intent(this,::class.java)
            startActivity(intent)*/
    }
}