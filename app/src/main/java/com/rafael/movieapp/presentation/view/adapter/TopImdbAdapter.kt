package com.rafael.movieapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.movie.Result
import com.rafael.movieapp.databinding.LayoutTopimdbMovieBinding

class TopImdbAdapter(private val list: MutableList<Result>) :
    RecyclerView.Adapter<TopImdbAdapter.CarouselViewHolder>() {

    private var itemClickListener: ((Result) -> Unit)? = null

    inner class CarouselViewHolder(private val binding: LayoutTopimdbMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result) {
            binding.apply {

                txtImdb.text = data.vote_average.toString()
                txtTitle.text = data.title
                Glide.with(posterImage)
                    .load("https://image.tmdb.org/t/p/w342/${data.backdrop_path}")
                    .into(binding.posterImage)
                itemView.setOnClickListener {
                    itemClickListener?.invoke(data)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_topimdb_movie, parent, false)
        return CarouselViewHolder(LayoutTopimdbMovieBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 8)
    }

    fun setItemClickListener(listener: (Result) -> Unit) {
        itemClickListener = listener
    }
}
