package com.saitejajanjirala.internshaalaprac2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.saitejajanjirala.internshaalaprac2.R
import com.saitejajanjirala.internshaalaprac2.fragments.Aboutinfo
import com.saitejajanjirala.internshaalaprac2.fragments.Dashboard
import com.saitejajanjirala.internshaalaprac2.fragments.Favourites
import com.saitejajanjirala.internshaalaprac2.fragments.Profile

class MainActivity : AppCompatActivity() {
    lateinit var navigationview:NavigationView
    lateinit var coordinatorlayout:CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerlayout)
        navigationview=findViewById(R.id.navigationview)
        coordinatorlayout=findViewById(R.id.coordinatorlayout)
        var previousmenuitem:MenuItem?=null
        frameLayout=findViewById(R.id.container)
        toolbar=findViewById(R.id.toolbar)
        settoolbar()
        opendashboard()
        navigationview.setNavigationItemSelectedListener {
            if(previousmenuitem!=null){
                previousmenuitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousmenuitem=it
            when(it.itemId){
                R.id.dashboard ->{
                    opendashboard()
                    //Toast.makeText(this@MainActivity,"dashboard",Toast.LENGTH_SHORT).show()
                }
                R.id.favourites ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container,
                        Favourites()
                    ).commit()
                    supportActionBar?.title="Favourites"
                    //Toast.makeText(this@MainActivity,"favourites",Toast.LENGTH_SHORT).show()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container,
                        Profile()
                    ).commit()
                    supportActionBar?.title="Profile"
                    Toast.makeText(this@MainActivity,"profile",Toast.LENGTH_SHORT).show()
                }
                R.id.info ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container,
                        Aboutinfo()
                    ).commit()
                    supportActionBar?.title="Info"
                    //Toast.makeText(this@MainActivity,"info",Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(navigationview,true)
            return@setNavigationItemSelectedListener true }
    }
    fun settoolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Naruto Fast Food"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle=ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navigationview,true)
        return true
    }
    fun opendashboard(){
        navigationview.setCheckedItem(R.id.dashboard)
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            Dashboard()
        ).commit()
        supportActionBar?.title="Dashboard"

    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.container)
        when(frag){
            !is Dashboard -> opendashboard()
            else->super.onBackPressed()
        }

    }

}
