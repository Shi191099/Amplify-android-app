package com.example.java_application

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.java_application.databinding.ActivityMainBinding
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var fabAuth: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize fabAuth using the correct ID from your layout
        fabAuth = binding.fabAuth

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fabAuth.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //setupAuthButton(UserData)
        setupAuthButton()


        UserData.isSignedInLiveData.observe(this, Observer<Boolean> { isSignedUp ->
            // update UI
            Log.i(TAG, "isSignedIn changed : $isSignedUp")

            if (isSignedUp) {
                fabAuth.setImageResource(R.drawable.ic_baseline_lock_open)
            } else {
                fabAuth.setImageResource(R.drawable.ic_baseline_lock)
            }
        })


    }
    // MainActivity.kt
// receive the web redirect after authentication
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Backend.handleWebUISignInResponse(requestCode, resultCode, data)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupAuthButton() {
        // register a click listener
        fabAuth.setOnClickListener { view ->

            val authButton = view as FloatingActionButton

            if (UserData.isSignedInLiveData.value ?: false) { // Update this line
                authButton.setImageResource(R.drawable.ic_baseline_lock)
                Backend.signOut()
            } else {
                authButton.setImageResource(R.drawable.ic_baseline_lock_open)
                Backend.signIn(this)
            }
        }
    }

}