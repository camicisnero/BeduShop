package org.bedu.bedushop.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.bedu.bedushop.fragments.CartFragment
import org.bedu.bedushop.R

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                resources.getResourceEntryName(R.id.fragment_home),
                resources.getResourceEntryName(R.id.fragment_cart),
                resources.getResourceEntryName(R.id.fragment_profile) -> {
                    changeBottomBarVisibility(bottomNavigationView, true)
                }
                else -> {
                    changeBottomBarVisibility(bottomNavigationView)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_search ->{
                Toast.makeText(this,getString(R.string.item_search_toast), Toast.LENGTH_SHORT).show()
            }
            R.id.item_help ->{
                val url:String = getString(R.string.item_help_link)
                openWebPage(url)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Opens webpage url
     * @param  url an absolute URL giving the webpage location
     */
    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val preferences = getSharedPreferences(CartFragment.PREFS_NAME, Context.MODE_PRIVATE)
        preferences.edit().clear().commit()
    }

    /**
     * Opens webpage url
     * @param  url an absolute URL giving the webpage location
     */
    private fun changeBottomBarVisibility(bottomNav: BottomNavigationView, show : Boolean = false) {
        val transition = Slide(Gravity.BOTTOM)
        transition.duration = 500
        transition.addTarget(bottomNav)
        TransitionManager.beginDelayedTransition(findViewById(R.id.activity_home), transition)

        bottomNav.visibility = if (show) View.VISIBLE else View.GONE
    }
}