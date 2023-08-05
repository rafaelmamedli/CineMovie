package com.rafael.movieapp.presentation.view.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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
import com.rafael.movieapp.data.util.POPULAR
import com.rafael.movieapp.data.util.POPULAR_MOVIE
import com.rafael.movieapp.data.util.RECENT
import com.rafael.movieapp.data.util.RECENT_MOVIE
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.TOP_RATED
import com.rafael.movieapp.data.util.TOP_RATED_MOVIE
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentHomeBinding
import com.rafael.movieapp.presentation.view.adapter.CarouselAdapter
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
        goToSeeAll()
        toDetail()

    }

    private fun toDetail(){
        adapterPopular.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
                    putString("type", POPULAR_MOVIE)
                    putParcelable(POPULAR, it)

                }
            )
        }

        adapterRecent.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
                    putString("type", RECENT_MOVIE)
                    putParcelable(RECENT, it)

                }
            )
        }

        adapterTopImdb.setItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,Bundle().apply {
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
        val horizontal = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val vertical = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontal2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        adapterPopular = PopularMovieAdapter(listPopularMovies)
        adapterRecent = RecentMovieAdapter(listRecentMovies, true)
        adapterTopImdb = CarouselAdapter(listTopRated)

        binding.apply {
            recyclerViewRecent.layoutManager = vertical
            recyclerViewPopular.layoutManager = horizontal
            recyclerViewTop.layoutManager = horizontal2

            recyclerViewPopular.adapter = adapterPopular
            recyclerViewRecent.adapter = adapterRecent
            recyclerViewTop.adapter = adapterTopImdb
        }
    }


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
                            val currentDateTime = LocalDateTime.now()
                            Log.d("TIME", currentDateTime.toString())
                            binding.progressBar.gone()

                            listPopularMovies.clear()
                            resource.data?.results?.let { movieList ->
                                listPopularMovies.addAll(movieList)
                                objectPopular = resource.data
                            }
                            adapterPopular.notifyDataSetChanged()

                            toast(listPopularMovies.toString())


                        }

                        Status.ERROR -> {
                            toast(resource.message)
                        }
                    }
                }
            }



       val job2 =  async {
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
                        adapterRecent.notifyDataSetChanged()

                    }

                    Status.ERROR -> {
                        toast(resource.message)
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
                        toast(resource.message)
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








