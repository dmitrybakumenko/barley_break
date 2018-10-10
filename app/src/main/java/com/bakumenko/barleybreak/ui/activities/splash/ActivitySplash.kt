package com.bakumenko.barleybreak.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.bakumenko.barleybreak.R
import com.bakumenko.barleybreak.ui.activities.ActivityBase
import com.bakumenko.barleybreak.ui.activities.main.ActivityMain
import com.transitionseverywhere.Rotate
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class ActivitySplash : ActivityBase() {

    private val DurSplash = 3000L

    private val _splashHandler: Handler = Handler()
    private val _splashRunnable: Runnable = Runnable {
        startActivity(Intent(this, ActivityMain::class.java))
        overridePendingTransition(-1, -1)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        updImgRotation()
    }

    override fun onResume() {
        super.onResume()

        postUpdImgRotation()
        _splashHandler.postDelayed(_splashRunnable, DurSplash)
    }

    override fun onPause() {
        super.onPause()

        _splashHandler.removeCallbacks(_splashRunnable)
    }

    private fun postUpdImgRotation() {
        with(img) {
            postDelayed({
                if (isShown) {
                    TransitionManager.beginDelayedTransition(pnlRoot, Rotate())
                    updImgRotation()
                    postUpdImgRotation()
                }
            }, 500)
        }

    }

    private fun updImgRotation() {
        img.rotation += Random().nextFloat() * 360
    }
}
