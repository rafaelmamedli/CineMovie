package com.rafael.movieapp.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.util.FAVOURITE
import com.rafael.movieapp.data.util.FAVOURITE_MOVIE
import com.rafael.movieapp.data.util.Status.*
import com.rafael.movieapp.data.util.disableBackPressed
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.databinding.FragmentFavoriteBinding
import com.rafael.movieapp.presentation.view.adapter.FavouriteAdapter
import com.rafael.movieapp.presentation.viewmodel.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    lateinit var adapter: FavouriteAdapter
    lateinit var binding: FragmentFavoriteBinding
    private var listFavMovies = mutableListOf<FavMovies>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavouriteAdapter(listFavMovies)
        binding.recyclerViewFavorite.adapter = adapter
        disableBackPressed()

    }

    override fun onResume() {
        super.onResume()
        deleteFavMovie()
        observeData()
        goToDetail()
    }


    private fun deleteFavMovie() {
        adapter.deleteItemClickListener {
            viewModel.deleteFavMovie(it)
        }
    }

    private fun goToDetail() {
        adapter.setItemClickListener {
            findNavController().navigate(
                R.id.action_favoriteFragment_to_detailFragment, Bundle().apply {
                    putString("type", FAVOURITE_MOVIE)
                    putParcelable(FAVOURITE, it)
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() = lifecycleScope.launch {
        viewModel.getFavMovies.collect { resource ->
            when (resource.status) {
                LOADING -> binding.progressBar.show()
                SUCCESS -> {
                    resource.data?.let {
                        binding.progressBar.gone()
                        listFavMovies.clear()
                        listFavMovies.addAll(it.reversed())
                        if (listFavMovies.isEmpty()) {
                            binding.emptyList.show()
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                ERROR -> Log.e("Error", resource.message.toString())

            }

        }
    }


}