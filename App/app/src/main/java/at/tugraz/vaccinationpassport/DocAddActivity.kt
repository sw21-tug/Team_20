package at.tugraz.vaccinationpassport

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import at.tugraz.vaccinationpassport.utils.changeLocale
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_doctor_adds_vaccines.*
import kotlinx.android.synthetic.main.activity_doctor_adds_vaccines.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DocAddActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = this.intent.extras?.get(resources.getString(R.string.language_key))
        changeLocale(language as String?, "en", this.resources)

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
}