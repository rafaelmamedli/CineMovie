package com.rafael.movieapp.presentation.theme

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.rafael.movieapp.R

/**
 * A selectable movie skin.
 *
 * Adding a new one is three steps: a palette in `colors.xml`, a
 * `Theme.CineMovie.<Name>` style in `themes.xml`, and one entry here — no
 * screen has to be touched, because every layout paints itself from theme
 * attributes.
 */
enum class CineTheme(
    val key: String,
    @StyleRes val styleRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val taglineRes: Int,
    @ColorRes val previewBackground: Int,
    @ColorRes val previewAccent: Int,
    @ColorRes val previewSurface: Int,
    @ColorRes val previewText: Int
) {
    MATRIX(
        key = "matrix",
        styleRes = R.style.Theme_CineMovie_Matrix,
        titleRes = R.string.theme_matrix,
        taglineRes = R.string.tagline_matrix,
        previewBackground = R.color.matrix_background,
        previewAccent = R.color.matrix_accent,
        previewSurface = R.color.matrix_surface_variant,
        previewText = R.color.matrix_text_primary
    ),
    DUNE(
        key = "dune",
        styleRes = R.style.Theme_CineMovie_Dune,
        titleRes = R.string.theme_dune,
        taglineRes = R.string.tagline_dune,
        previewBackground = R.color.dune_background,
        previewAccent = R.color.dune_accent,
        previewSurface = R.color.dune_surface_variant,
        previewText = R.color.dune_text_primary
    );

    companion object {
        val DEFAULT: CineTheme = MATRIX

        fun fromKey(key: String?): CineTheme =
            values().firstOrNull { it.key == key } ?: DEFAULT
    }
}
