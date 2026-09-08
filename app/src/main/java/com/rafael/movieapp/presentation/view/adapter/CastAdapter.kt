package com.rafael.movieapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafael.movieapp.data.util.glide
import com.rafael.movieapp.data.models.remote.detail.Cast
import com.rafael.movieapp.databinding.LayoutCrewBinding

class CastAdapter(private val list: MutableList<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    class ViewHolder(private val binding: LayoutCrewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Cast) {
            binding.apply {
                txtTitle.text = data.name
                imageViewArtist.glide(data.profile_path)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewHolder = LayoutCrewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(itemViewHolder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
}