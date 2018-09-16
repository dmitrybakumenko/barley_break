package com.bakumenko.barley_break.barley_break

import android.app.Application
import com.bakumenko.barley_break.barley_break.helpers.ExceptionHelper

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ExceptionHelper::bindReporter(this);
    }
}