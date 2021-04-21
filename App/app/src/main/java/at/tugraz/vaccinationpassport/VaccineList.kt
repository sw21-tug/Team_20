package at.tugraz.vaccinationpassport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vaccine_list.*

class VaccineList : AppCompatActivity() {
    private lateinit var vaccineListAdapter : VaccineListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine_list)

        vaccineListAdapter = VaccineListAdapter(mutableListOf())
        rvVaccineList.adapter = vaccineListAdapter;
    }
}