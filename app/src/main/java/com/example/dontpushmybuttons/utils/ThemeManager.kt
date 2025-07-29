package com.example.dontpushmybuttons.utils

import android.content.Context
import android.content.SharedPreferences

class ThemeManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val THEME_KEY = "is_dark_theme"
        @Volatile
        private var INSTANCE: ThemeManager? = null

        fun getInstance(context: Context): ThemeManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemeManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    fun isDarkTheme(systemDefault: Boolean): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, systemDefault)
    }

    fun setDarkTheme(isDark: Boolean) {
        sharedPreferences.edit()
            .putBoolean(THEME_KEY, isDark)
            .apply()
    }
}
