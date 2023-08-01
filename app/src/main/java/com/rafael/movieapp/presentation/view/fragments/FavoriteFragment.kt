package com.rafael.movieapp.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentFavoriteBinding
import com.rafael.movieapp.presentation.view.adapter.FavouriteAdapter
import com.rafael.movieapp.presentation.viewmodel.LocalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : Fragment() {


    private val viewModel: LocalViewModel by viewModels()
    lateinit var adapter: FavouriteAdapter
    lateinit var binding: FragmentFavoriteBinding
    private var listFavMovies = mutableListOf<FavMovies>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = FavouriteAdapter(listFavMovies)
        binding.recyclerViewFavorite.adapter = adapter


    }

    override fun onResume() {
        super.onResume()
         deleteFavMovie()
        observeData()


    }


    private fun deleteFavMovie(){
        adapter.setItemClickListener {
            viewModel.deleteFavMovie(it)
        }
    }





    private fun observeData() = lifecycleScope.launch {
        viewModel.getFavMovies.collect { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.progressBar.show()
                }

                Status.SUCCESS -> {
                    resource.data?.let {
                        binding.progressBar.gone()
                        listFavMovies.clear()
                        listFavMovies.addAll(it)
                        adapter.notifyDataSetChanged()

                      //  toast(it.size.toString())
                    }
                }

                Status.ERROR -> {

                    toast(resource.message)
                }
            }

        }
    }


}