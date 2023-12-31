package com.rafael.movieapp.presentation.view.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.rafael.movieapp.R
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {


    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(layoutInflater)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 1000L)

        val bottomBar = requireActivity().findViewById<ChipNavigationBar>(R.id.bottom_nav_bar)
        bottomBar.gone()
        return binding.root

    }







}