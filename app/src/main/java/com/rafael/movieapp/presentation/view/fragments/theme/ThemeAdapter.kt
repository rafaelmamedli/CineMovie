package com.rafael.movieapp.presentation.view.fragments.theme

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.rafael.movieapp.databinding.LayoutThemeItemBinding
import com.rafael.movieapp.presentation.theme.CineTheme

class ThemeAdapter(
    private val themes: List<CineTheme>,
    private var selected: CineTheme,
    private val onThemeClick: (CineTheme) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    inner class ThemeViewHolder(private val binding: LayoutThemeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(theme: CineTheme) {
            val context = binding.root.context
            val accent = ContextCompat.getColor(context, theme.previewAccent)
            val isSelected = theme == selected

            binding.apply {
                previewStrip.setBackgroundColor(
                    ContextCompat.getColor(context, theme.previewBackground)
                )
                swatchAccent.tintWith(accent)
                swatchSurface.tintWith(ContextCompat.getColor(context, theme.previewSurface))
                swatchText.tintWith(ContextCompat.getColor(context, theme.previewText))

                txtThemeName.setText(theme.titleRes)
                txtThemeTagline.setText(theme.taglineRes)

                imgSelected.visibility = if (isSelected) View.VISIBLE else View.GONE
                ImageViewCompat.setImageTintList(imgSelected, ColorStateList.valueOf(accent))

                cardTheme.strokeWidth = if (isSelected) SELECTED_STROKE_DP.dpToPx() else 1
                cardTheme.setStrokeColor(
                    if (isSelected) accent
                    else ContextCompat.getColor(context, theme.previewSurface)
                )
                cardTheme.setOnClickListener { onThemeClick(theme) }
            }
        }

        private fun View.tintWith(color: Int) {
            ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))
        }

        private fun Int.dpToPx(): Int =
            (this * binding.root.resources.displayMetrics.density).toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val binding = LayoutThemeItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(themes[position])
    }

    override fun getItemCount(): Int = themes.size

    private companion object {
        const val SELECTED_STROKE_DP = 2
    }
}
