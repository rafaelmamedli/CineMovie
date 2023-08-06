package com.rafael.movieapp.presentation.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.POPULAR
import com.rafael.movieapp.data.util.POPULAR_MOVIE
import com.rafael.movieapp.data.util.RECENT
import com.rafael.movieapp.data.util.RECENT_MOVIE
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.TOP_RATED
import com.rafael.movieapp.data.util.TOP_RATED_MOVIE
import com.rafael.movieapp.data.util.disableBackPressed
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.databinding.FragmentHomeBinding
import com.rafael.movieapp.presentation.view.adapter.TopImdbAdapter
import com.rafael.movieapp.presentation.view.adapter.PopularMovieAdapter
import com.rafael.movieapp.presentation.view.adapter.RecentMovieAdapter
import com.rafael.movieapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterPopular: PopularMovieAdapter
    private lateinit var adapterRecent: RecentMovieAdapter
    private lateinit var adapterTopImdb: TopImdbAdapter
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
        val bottomBar = requireActivity().findViewById<ChipNavigationBar>(R.id.bottom_nav_bar)
        bottomBar.show()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAdapters()
        observeData()
        goToSeeAll()
        toDetail()
        disableBackPressed()


    }

    private fun toDetail() {
        adapterPopular.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment, Bundle().apply {
                    putString("type", POPULAR_MOVIE)
                    putParcelable(POPULAR, it)

                }
            )
        }

        adapterRecent.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment, Bundle().apply {
                    putString("type", RECENT_MOVIE)
                    putParcelable(RECENT, it)

                }
            )
        }

        adapterTopImdb.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment, Bundle().apply {
                    putString("type", TOP_RATED_MOVIE)
                    putParcelable(TOP_RATED, it)

                }
            )
        }
    }


    private fun goToSeeAll() {

        binding.seeAll1.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", TOP_RATED_MOVIE)
                    putParcelable(TOP_RATED, objectTopRated)

                })

        }
        binding.seeAll2.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", POPULAR_MOVIE)
                    putParcelable(POPULAR, objectPopular)

                })
        }

        binding.seeAll3.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seeAllFragment,
                Bundle().apply {
                    putString("type", RECENT_MOVIE)
                    putParcelable(RECENT, objectRecent)

                })
        }
    }

    private fun getAdapters() {
        adapterPopular = PopularMovieAdapter(listTopRated)
        adapterRecent = RecentMovieAdapter(listRecentMovies, true)
        adapterTopImdb = TopImdbAdapter(listPopularMovies)

        binding.apply {
            recyclerViewPopular.adapter = adapterPopular
            recyclerViewRecent.adapter = adapterRecent
            recyclerViewTop.adapter = adapterTopImdb
        }
    }


    private fun showAlertDialog() {
        val alertDialog = Dialog(requireContext())
        with(alertDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.layout_custom_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val btnRetry: Button = alertDialog.findViewById(R.id.btnRetry)

        btnRetry.setOnClickListener {
            viewModel.refreshData()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData() {
        lifecycleScope.launch {
            val job1 = async {
                viewModel.popularMovieList.collect { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            binding.progressBar.show()
                        }

                        Status.SUCCESS -> {
                            binding.progressBar.gone()
                            binding.nestedView.show()
                            binding.txtErrorMessage.gone()
                            listPopularMovies.clear()
                            resource.data?.results?.let { movieList ->
                                listPopularMovies.addAll(movieList)
                                objectPopular = resource.data
                            }
                            adapterPopular.notifyDataSetChanged()

                        }

                        Status.ERROR -> {
                            binding.progressBar.gone()
                            binding.txtErrorMessage.show()
                            showAlertDialog()
                            Log.e("ERROR",resource.message.toString())
                        }
                    }
                }
            }


            val job2 = async {
                viewModel.recentMovieList.collect { resource ->

                    when (resource.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            listRecentMovies.clear()
                            resource.data?.results?.let { movieListPopular ->
                                val sortedDataForDate =
                                    movieListPopular.sortedByDescending { it.release_date }
                                listRecentMovies.addAll(sortedDataForDate)
                                objectRecent = resource.data
                            }
                            adapterRecent.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            Log.e("ERROR",resource.message.toString())
                        }
                    }
                }
            }

            val job3 = async {
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
                                objectTopRated = resource.data
                            }
                            adapterTopImdb.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            Log.e("ERROR",resource.message.toString())

                        }
                    }
                }


            }
            job1.await()
            job2.await()
            job3.await()


        }
    }


}








