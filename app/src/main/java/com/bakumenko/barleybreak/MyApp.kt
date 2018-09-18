package com.bakumenko.barleybreak

import android.annotation.SuppressLint
import android.app.Application
import com.bakumenko.barleybreak.helpers.ExceptionHandler

@SuppressLint("Registered")
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
        ExceptionHandler.bind(this)
    }

    companion object {
        private var app: MyApp? = null

        var instance: MyApp
            get() = app ?: throw IllegalStateException("MyApp isn't create")
            private set(value) {
                app = value
            }
    }
}