package com.example.filemanager

import ExternalFragment
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.filemanager.Fragment.HomeFragment
import com.example.filemanager.Fragment.InternalFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawLayout : DrawerLayout

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawLayout = findViewById(R.id.draw_layout)

        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
        drawLayout.addDrawerListener(toggle)
        toggle.syncState()

        val homeFragment: HomeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame,homeFragment).commit()
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.home -> {
                val homeFragment: HomeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame,homeFragment).addToBackStack(null).commit()
            }
            R.id.sdcard -> {
                val externalFragment: ExternalFragment = ExternalFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame,externalFragment).addToBackStack(null).commit()

            }
            R.id.internal -> {
                val internalFragment: InternalFragment = InternalFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame,internalFragment).addToBackStack(null).commit()
            }

        }
        drawLayout.closeDrawer(GravityCompat.START)
        return true
    }




    override fun onBackPressed() {
        supportFragmentManager.popBackStackImmediate()
        if(drawLayout.isDrawerOpen(GravityCompat.START)){
            drawLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }
}
