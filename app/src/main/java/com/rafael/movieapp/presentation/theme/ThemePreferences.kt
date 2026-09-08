package com.rafael.movieapp.presentation.theme

import android.content.Context

/** Remembers the skin the user picked. */
class ThemePreferences(context: Context) {

    private val prefs =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var theme: CineTheme
        get() = CineTheme.fromKey(prefs.getString(KEY_THEME, null))
        set(value) {
            prefs.edit().putString(KEY_THEME, value.key).apply()
        }

    private companion object {
        const val PREFS_NAME = "cine_appearance"
        const val KEY_THEME = "selected_theme"
    }
}
