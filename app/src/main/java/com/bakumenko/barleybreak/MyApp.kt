package com.bakumenko.barleybreak

import android.annotation.SuppressLint
import android.app.Application
import com.bakumenko.barleybreak.data.preferences.PrefsHolder
import com.bakumenko.barleybreak.helpers.ExceptionHandler

@SuppressLint("Registered")
class MyApp : Application() {

    val environment: Environment by lazy {
        Environment()
    }

    override fun onCreate() {
        super.onCreate()

        _app = this
        ExceptionHandler.bind(this)
    }

    companion object {
        private var _app: MyApp? = null

        var instance: MyApp
            get() = _app ?: throw IllegalStateException("MyApp isn't create")
            private set(value) {
                _app = value
            }
    }

    inner class Environment {
        val preferences = PrefsHolder(this@MyApp)
    }
}