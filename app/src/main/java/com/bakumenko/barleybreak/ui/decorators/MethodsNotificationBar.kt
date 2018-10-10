package com.bakumenko.barleybreak.ui.decorators

import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.bakumenko.barleybreak.R
import com.bakumenko.barleybreak.helpers.getColor

fun decorateNotificationBar(activity: Activity?, window: Window?) {
    if (activity != null && window != null) {
        decorateNotificationBar(activity, window, getColor(activity, R.color.colorNotificationBar))
    }
}

private fun decorateNotificationBar(activity: Activity, window: Window, color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            activity.actionBar!!.elevation = 0f
        } catch (ignored: Exception) {

        }
    }
}