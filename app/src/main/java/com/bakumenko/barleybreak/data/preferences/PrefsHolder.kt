package com.bakumenko.barleybreak.data.preferences

import android.content.Context

class PrefsHolder(private val context: Context) {
    val game: PrefsGame by lazy {
        PrefsGame(context)
    }
}