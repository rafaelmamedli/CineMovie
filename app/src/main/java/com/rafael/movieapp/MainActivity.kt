package com.rafael.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.rafael.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavBar.setItemSelected(R.id.home)
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {

                R.id.home -> navController.navigate(R.id.homeFragment)

                R.id.favourites ->  navController.navigate(R.id.favoriteFragment)

                R.id.search -> navController.navigate(R.id.searchFragment)


            }
        }
    }




}