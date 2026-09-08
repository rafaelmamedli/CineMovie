package com.rafael.movieapp.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafael.movieapp.data.util.glide
import com.rafael.movieapp.data.models.remote.movie.Result
import com.rafael.movieapp.databinding.LayoutSearchMovieBinding

class SearchAdapter(private val list: MutableList<Result>): RecyclerView.Adapter<SearchAdapter.ProductViewHolder>() {



    private var itemClickListener: ((Result) -> Unit)? = null


    inner class ProductViewHolder(private val binding: LayoutSearchMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(data: Result) {
            binding.apply {
                posterSearch.glide(data.poster_path)
                    txtImdb.text = data.vote_average.toString()
                    itemView.setOnClickListener {
                        itemClickListener?.invoke(data)
                    }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutSearchMovieBinding.inflate(
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

    fun setItemClickListener(listener: (Result) -> Unit) {
        itemClickListener = listener
    }
}