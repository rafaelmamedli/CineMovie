package com.rafael.movieapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.data.models.remote.movie.Result
import com.rafael.movieapp.data.util.formatDate
import com.rafael.movieapp.databinding.LayoutRecentMovieBinding



class RecentMovieAdapter(var list: MutableList<Result>, val isHomeScreen: Boolean) :
    RecyclerView.Adapter<RecentMovieAdapter.ProductViewHolder>() {


    private var itemClickListener: ((Result) -> Unit)? = null

    inner class ProductViewHolder(private val binding: LayoutRecentMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(data: Result) {
            binding.apply {
                txtTitle.text = data.title
                txtImdb.text = data.vote_average.toString()
                Glide.with(posterRecent)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterRecent)

                txtDate.text = data.release_date?.formatDate()
                itemView.setOnClickListener {
                    itemClickListener?.invoke(data)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutRecentMovieBinding.inflate(
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
        return return if (isHomeScreen){
            minOf(list.size, 6)
        } else {
            list.size
        }
    }

    fun setItemClickListener(listener: (Result) -> Unit) {
        itemClickListener = listener
    }
}
