package com.rafael.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
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

        applyWindowInsets()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Never stack duplicates of the top level screens, and always come back to Home.
        val topLevelNavOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(R.id.homeFragment, false)
            .build()

        binding.bottomNavBar.setItemSelected(R.id.home)
        binding.bottomNavBar.setOnItemSelectedListener { itemId ->
            if (navController.currentDestination?.id == itemId.toDestinationId()) {
                return@setOnItemSelectedListener
            }
            when (itemId) {
                R.id.home -> navController.navigate(R.id.homeFragment, null, topLevelNavOptions)
                R.id.favourites ->
                    navController.navigate(R.id.favoriteFragment, null, topLevelNavOptions)
                R.id.search -> navController.navigate(R.id.searchFragment, null, topLevelNavOptions)
            }
        }

        // Keep the bottom bar in sync when the user navigates back.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNavBar.setItemSelected(R.id.home)
                R.id.favoriteFragment -> binding.bottomNavBar.setItemSelected(R.id.favourites)
                R.id.searchFragment -> binding.bottomNavBar.setItemSelected(R.id.search)
            }
        }
    }

    /**
     * Apps that target Android 15 (API 35) always draw edge to edge, so the window
     * insets have to be consumed manually to keep content out of the status bar and
     * the navigation bar.
     */
    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val bars = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            windowInsets
        }
    }

    /** Maps a bottom bar menu item id to its navigation destination id. */
    private fun Int.toDestinationId(): Int = when (this) {
        R.id.home -> R.id.homeFragment
        R.id.favourites -> R.id.favoriteFragment
        R.id.search -> R.id.searchFragment
        else -> 0
    }
}
