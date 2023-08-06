package com.rafael.movieapp.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.SEARCHED
import com.rafael.movieapp.data.util.SEARCHED_MOVIE
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.disableBackPressed
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentSearchBinding
import com.rafael.movieapp.presentation.view.adapter.SearchAdapter
import com.rafael.movieapp.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    private var list = mutableListOf<Result>()
    private var popularMovieList = mutableListOf<Result>()
    private var popularMoviesLoaded = false // Flag to track if popular movies have been loaded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val item = menu.findItem(R.id.search_word)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@SearchFragment)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchAdapter(list)
        binding.recyclerViewSearch.adapter = adapter
        observe()
        toDetail()
        disableBackPressed()
        setHasOptionsMenu(true)
    }

    private fun toDetail(){
        adapter.setItemClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_detailFragment,Bundle().apply {
                    putString("type", SEARCHED_MOVIE)
                    putParcelable(SEARCHED, it)
                }
            )
        }}

    private fun observe() {
        lifecycleScope.launch {
            viewModel.popularMovieList.collect { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressBar.show()

                    }

                    Status.SUCCESS -> {
                        binding.progressBar.gone()
                        binding.txtErrorMessage.gone()

                        val movieList = resource.data?.results
                        if (movieList != null && !popularMoviesLoaded) {
                            popularMovieList.clear()
                            popularMovieList.addAll(movieList)
                            list.addAll(movieList)
                            popularMoviesLoaded = true
                            adapter.notifyDataSetChanged()
                        }
                    }

                    Status.ERROR -> {
                        binding.progressBar.gone()
                        binding.txtErrorMessage.show()

                        toast(resource.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.movieByName.collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val searchedItems = resource.data?.results
                        list.clear()
                        searchedItems?.let { list.addAll(it) }
                        adapter.notifyDataSetChanged()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        lifecycleScope.launch {
            if (newText.isNullOrEmpty()) {
                // If the search query is empty, display popular movies
                list.clear()
                if (popularMoviesLoaded) {
                    list.addAll(popularMovieList)
                    adapter.notifyDataSetChanged()
                } else {
                    viewModel.getPopularMovies()
                }
            } else {
                // If there's a search query, fetch movies by name
                newText?.let { viewModel.getMovieByName(it) }
            }
        }

        return true
    }
}
