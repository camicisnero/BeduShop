package org.bedu.bedushop

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val appBar = findViewById<Toolbar>(R.id.app_bar)
        this.setSupportActionBar(appBar)

        bottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.homeActivity_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_search->{
                Toast.makeText(this,"La busqueda esta desabilitada", Toast.LENGTH_SHORT).show()
            }
            R.id.item_help->{
                val url:String = "https://www.bedu.org"
                openWebPage(url)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}