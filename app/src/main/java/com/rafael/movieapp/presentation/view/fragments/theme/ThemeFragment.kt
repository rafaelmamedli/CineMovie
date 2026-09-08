package com.rafael.movieapp.presentation.view.fragments.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rafael.movieapp.databinding.FragmentThemeBinding
import com.rafael.movieapp.presentation.theme.CineTheme
import com.rafael.movieapp.presentation.theme.ThemePreferences

/**
 * Lets the user dress the app up as one of the supported movies. Applying a
 * skin only writes the preference and recreates the activity — MainActivity
 * installs the saved theme before it inflates anything.
 */
class ThemeFragment : Fragment() {

    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = ThemePreferences(requireContext())

        binding.recyclerViewThemes.adapter = ThemeAdapter(
            themes = CineTheme.values().toList(),
            selected = preferences.theme
        ) { picked ->
            if (picked != preferences.theme) {
                preferences.theme = picked
                requireActivity().recreate()
            }
        }

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
