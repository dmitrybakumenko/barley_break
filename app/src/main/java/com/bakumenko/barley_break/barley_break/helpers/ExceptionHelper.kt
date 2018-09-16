package com.bakumenko.barley_break.barley_break.helpers

import android.content.Context

class ExceptionHelper : Thread.UncaughtExceptionHandler {

    public fun bindReporter(context: Context) = {

    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}