package com.bakumenko.barleybreak.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

fun getDrawable(c: Context, drawableId: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        c.resources.getDrawable(drawableId, c.theme)
    } else {
        c.resources.getDrawable(drawableId)
    }
}

fun getColor(c: Context, id: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        c.resources.getColor(id, c.theme)
    } else {
        c.resources.getColor(id)
    }
}