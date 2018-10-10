package com.bakumenko.barleybreak.data.preferences

import android.content.Context
import android.content.SharedPreferences

abstract class PrefsBase(fileName: String, context: Context, mode: Int = Context.MODE_PRIVATE) {
    protected val prefs: SharedPreferences = context.getSharedPreferences("${context.packageName}.$fileName", mode)
}