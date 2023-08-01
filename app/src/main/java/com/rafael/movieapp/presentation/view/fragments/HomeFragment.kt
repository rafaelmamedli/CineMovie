package com.rafael.movieapp.presentation.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentHomeBinding
import com.rafael.movieapp.presentation.view.adapter.CarouselAdapter
import com.rafael.movieapp.presentation.view.adapter.PopularMovieAdapter
import com.rafael.movieapp.presentation.view.adapter.RecentMovieAdapter
import com.rafael.movieapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterPopular: PopularMovieAdapter
    private lateinit var adapterRecent: RecentMovieAdapter
    private lateinit var adapterTopImdb: CarouselAdapter
    private val listPopularMovies = mutableListOf<Result>()
    private val listRecentMovies = mutableListOf<Result>()
    private val listTopRated = mutableListOf<Result>()

    var objectTopRated: Movie? = null
    var objectRecent: Movie? = null
    var objectPopular: Movie? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAdapters()
        observeData()
        toSeeAll()
        toDetail()





    }

    private fun toDetail(){
        adapterPopular.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
                    putString("type", "popular_movie")
                    putParcelable("popular", it)

                }
            )
        }

        adapterRecent.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
                    putString("type", "recent_movie")
                    putParcelable("recent", it)

                }
            )
        }

        adapterTopImdb.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
                    putString("type", "top_rated_movie")
                    putParcelable("top_rated", it)

                }
            )
        }
    }



    private fun toSeeAll() {

        binding.seeAll1.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", "top_rated_movie")
                    putParcelable("top_rated", objectTopRated)

                })

        }
        binding.seeAll2.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", "popular_movie")
                    putParcelable("popular", objectPopular)

                })
        }

        binding.seeAll3.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", "recent_movie")
                    putParcelable("recent", objectRecent)

                })
        }
    }

    private fun getAdapters() {
        val horizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val vertical = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterPopular = PopularMovieAdapter(listPopularMovies)
        adapterRecent = RecentMovieAdapter(listRecentMovies, true)
        adapterTopImdb = CarouselAdapter(listTopRated)

        binding.apply {
            recyclerViewRecent.layoutManager = vertical
            recyclerViewPopular.layoutManager = horizontal

            recyclerViewPopular.adapter = adapterPopular
            recyclerViewRecent.adapter = adapterRecent

            recyclerViewTop.apply {
                adapter = adapterTopImdb
                set3DItem(true)
                setAlpha(true)
                setInfinite(true)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.popularMovieList.collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressBar.show()
                    }

                    Status.SUCCESS -> {
                        val currentDateTime = LocalDateTime.now()
                        Log.d("TIME", currentDateTime.toString())


                        binding.progressBar.gone()
                        listPopularMovies.clear()
                        resource.data?.results?.let { movieList ->
                            listPopularMovies.addAll(movieList)
                            adapterPopular.notifyDataSetChanged()
                            objectPopular = resource.data
                        }


                    }

                    Status.ERROR -> {
                        toast(resource.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.recentMovieList.collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }

                    Status.SUCCESS -> {
                        val currentDateTime = LocalDateTime.now()
                        Log.d("TIME", "Recent" + currentDateTime.toString())

                        listRecentMovies.clear()
                        resource.data?.results?.let { movieListPopular ->
                            val sortedDataForDate =
                                movieListPopular.sortedByDescending { it.release_date }
                            listRecentMovies.addAll(sortedDataForDate)
                            objectRecent = resource.data

                        }
                    }

                    Status.ERROR -> {
                        toast(resource.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.topRatedMovieList.collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }

                    Status.SUCCESS -> {
                        val currentDateTime = LocalDateTime.now()
                        Log.d("TIME", "Top" + currentDateTime.toString())

                        listTopRated.clear()
                        resource.data?.results?.let { movieListTopRated ->
                            listTopRated.addAll(movieListTopRated)
                            adapterTopImdb.notifyDataSetChanged()
                            objectTopRated = resource.data

                        }


                    }

                    Status.ERROR -> {
                        toast(resource.message)
                    }
                }
            }
        }

    }


}








