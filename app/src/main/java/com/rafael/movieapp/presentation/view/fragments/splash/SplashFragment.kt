package com.rafael.movieapp.presentation.view.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.rafael.movieapp.R
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.databinding.FragmentSplashBinding
import com.rafael.movieapp.presentation.theme.ThemePreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        requireActivity().findViewById<ChipNavigationBar>(R.id.bottom_nav_bar).gone()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtTagline.setText(ThemePreferences(requireContext()).theme.taglineRes)

        // Tied to the view lifecycle: rotating or leaving the app can no longer
        // trigger a navigation on a destroyed fragment.
        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DELAY_MS)
            val navController = findNavController()
            if (navController.currentDestination?.id == R.id.splashFragment) {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val SPLASH_DELAY_MS = 1000L
    }
}
