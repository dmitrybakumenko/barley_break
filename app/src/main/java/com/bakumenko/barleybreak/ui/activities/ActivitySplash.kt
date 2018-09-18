package com.bakumenko.barleybreak.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import com.bakumenko.barleybreak.R
import com.transitionseverywhere.Rotate
import com.transitionseverywhere.TransitionManager
import java.util.*

class ActivitySplash : AppCompatActivity() {

    private val DurSplash = 3000L

    private val _splashHandler: Handler = Handler()
    private val _splashRunnable: Runnable = Runnable {
        startActivity(Intent(this, ActivityMain::class.java))
        overridePendingTransition(-1, -1)
        finish()
    }

    private lateinit var _img: ImageView
    private lateinit var _pnlRoot: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        _img = findViewById(R.id.img)
        _pnlRoot = findViewById(R.id.pnlRoot)

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

    fun postUpdImgRotation() {
        _img.postDelayed({
            if (_img.isShown) {
                TransitionManager.beginDelayedTransition(_pnlRoot, Rotate())
                updImgRotation()

                postUpdImgRotation()
            }
        }, 500)
    }

    private fun updImgRotation() {
        _img.rotation += Random().nextFloat() * 360
    }
}
