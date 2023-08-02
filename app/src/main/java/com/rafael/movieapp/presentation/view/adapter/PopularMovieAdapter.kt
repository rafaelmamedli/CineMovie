package com.rafael.movieapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.databinding.PopularMovieItemBinding

class PopularMovieAdapter(var list: MutableList<Result>) :
    RecyclerView.Adapter<PopularMovieAdapter.ProductViewHolder>() {

    private var itemClickListener: ((Result) -> Unit)? = null


    inner class ProductViewHolder(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Result) {


            binding.apply {
                txtTitle.text = data.title
                txtImdb.text = data.vote_average.toString()
                Glide.with(posterImage)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterImage)
                itemView.setOnClickListener {
                    itemClickListener?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = PopularMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return minOf(list.size, 6)
    }

    fun setItemClickListener(listener: (Result) -> Unit) {
        itemClickListener = listener
    }
}