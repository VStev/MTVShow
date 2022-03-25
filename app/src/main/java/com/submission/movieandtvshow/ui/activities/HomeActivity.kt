package com.submission.movieandtvshow.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(){

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.host_fragment)
        binding.bottomNavView.setupWithNavController(navController)
    }
}