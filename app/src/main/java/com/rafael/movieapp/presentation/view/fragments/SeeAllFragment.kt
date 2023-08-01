package com.rafael.movieapp.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.databinding.FragmentSeeAllBinding
import com.rafael.movieapp.presentation.view.adapter.RecentMovieAdapter
import com.rafael.movieapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SeeAllFragment : Fragment() {

    private lateinit var binding: FragmentSeeAllBinding
    private lateinit var adapter: RecentMovieAdapter
    private val list = arrayListOf<Result>()
    private var objectMovie: Movie? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeeAllBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterSetter()
        getMovies()
        toDetail()

    }

    private fun toDetail(){
        adapter.setItemClickListener {
            findNavController().navigate(
                R.id.action_seeAllFragment_to_detailFragment,Bundle().apply {
                    putString("type", "all_movie")
                    putParcelable("all", it)

                }
            )
        }
    }

    private fun adapterSetter() {
        val vertical = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = RecentMovieAdapter(list, false)
        binding.recyclerViewAllMovies.layoutManager = vertical
        binding.recyclerViewAllMovies.adapter = adapter
    }


    private fun getMovies() {
        lifecycleScope.launch {
            val type = arguments?.getString("type", null)
            type?.let {
                when (it) {
                    "top_rated_movie" -> {
                        list.clear()
                        objectMovie = arguments?.getParcelable("top_rated")
                        val obj = objectMovie?.results
                        obj?.let { it1 -> list.addAll(it1) }
                    }

                    "popular_movie" -> {
                        list.clear()
                        objectMovie = arguments?.getParcelable("popular")
                        val obj = objectMovie?.results
                        obj?.let { it1 -> list.addAll(it1) }
                    }

                    "recent_movie" -> {
                        list.clear()
                        objectMovie = arguments?.getParcelable("recent")
                        val obj = objectMovie?.results
                        obj?.let { it1 -> list.addAll(it1) }

                    }

                }


            }
        }
    }


}