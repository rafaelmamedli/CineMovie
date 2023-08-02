package com.rafael.movieapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.databinding.SearchMovieItemBinding

class SearchAdapter(private val list: MutableList<Result>): RecyclerView.Adapter<SearchAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(private val binding: SearchMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(data: Result) {
            binding.apply {
                Glide.with(posterSearch)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterSearch)



            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = SearchMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
}