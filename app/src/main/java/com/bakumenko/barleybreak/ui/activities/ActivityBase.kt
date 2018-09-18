package com.bakumenko.barleybreak.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bakumenko.barleybreak.ui.decorators.decorateNotificationBar

@SuppressLint("Registered")
open class ActivityBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        decorateNotificationBar(this, this.window)
    }
}