package com.hoodable

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        val userName = findViewById<TextView>(R.id.userName)
        val fcm_token = SharedPrefManager.getInstance(this@HomeActivity).deviceToken

        userName.setText(fcm_token)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


      /*    val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
          val navigationView = findViewById<NavigationView>(R.id.nav_view)
          // Passing each menu ID as a set of Ids because each
          // menu should be considered as top level destinations.
          mAppBarConfiguration = AppBarConfiguration.Builder(
              R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
              R.id.nav_tools, R.id.nav_share, R.id.nav_send
          )
              .setDrawerLayout(drawer)
              .build()
          val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
          NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
          NavigationUI.setupWithNavController(navigationView, navController)*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        ///   menuInflater.inflate(R.menu.home, menu)
        return true
    }

    /*  override fun onSupportNavigateUp(): Boolean {
          val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
          return NavigationUI.navigateUp(
              navController,
              mAppBarConfiguration!!
          ) || super.onSupportNavigateUp()
      }*/

    override fun onBackPressed() {
        /// super.onBackPressed()
        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
