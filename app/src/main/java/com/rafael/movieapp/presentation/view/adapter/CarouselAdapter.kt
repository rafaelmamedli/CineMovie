package com.rafael.movieapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafael.movieapp.R
import com.rafael.movieapp.databinding.TopimdbMovieItemBinding
import com.rafael.movieapp.data.models.remote.Result

class CarouselAdapter(private val list: MutableList<Result>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private var itemClickListener: ((Result) -> Unit)? = null

    inner class CarouselViewHolder(private val binding: TopimdbMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result) {
            binding.apply {
                //  txtTitle.text = data.title
                Glide.with(image)
                    .load("https://image.tmdb.org/t/p/w342/${data.poster_path}")
                    .into(binding.image)
                itemView.setOnClickListener {
                    itemClickListener?.invoke(data)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.topimdb_movie_item, parent, false)
        return CarouselViewHolder(TopimdbMovieItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 8)    }

    fun setItemClickListener(listener: (Result) -> Unit) {
        itemClickListener = listener
    }
}
