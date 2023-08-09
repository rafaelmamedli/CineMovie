package com.rafael.movieapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.util.formatDate
import com.rafael.movieapp.databinding.LayoutFavMovieBinding

class FavouriteAdapter(private val list: MutableList<FavMovies>) :
    RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

    private var itemClickListener: ((FavMovies) -> Unit)? = null
    private var deleteClickListener: ((FavMovies) -> Unit)? = null


    inner class ProductViewHolder(private val binding: LayoutFavMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(data: FavMovies) {
            binding.apply {
                txtTitle.text = data.title
                txtImdb.text = data.vote_average.toString()
                Glide.with(posterRecent)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterRecent)
                txtDate.text = data.release_date?.formatDate()
                btnDelete.setOnClickListener {
                    deleteClickListener?.invoke(data)
                }

                itemView.setOnClickListener {
                    itemClickListener?.invoke(data)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteAdapter.ProductViewHolder {
        val itemView = LayoutFavMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }


    fun setItemClickListener(listener: (FavMovies) -> Unit) {
        itemClickListener = listener
    }
    fun deleteItemClickListener(listener: (FavMovies) -> Unit) {
        deleteClickListener = listener
    }
}