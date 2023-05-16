package com.example.attractions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.attractions.R

class MainActivity : AppCompatActivity() {
    var navController: NavController? = null
    var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        mToolbar?.setupWithNavController(navController!!)
        mToolbar?.setNavigationOnClickListener {
            // Handle the back button event and return to override
            // the default behavior the same way as the OnBackPressedCallback.
            // TODO(reason: handle custom back behavior here if desired.)

            // If no custom behavior was handled perform the default action.
            navController?.navigateUp() == true || super.onSupportNavigateUp()
        }
    }
}