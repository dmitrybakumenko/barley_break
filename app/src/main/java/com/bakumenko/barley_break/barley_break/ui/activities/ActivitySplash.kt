package com.bakumenko.barley_break.barley_break.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import com.bakumenko.barley_break.barley_break.R
import com.transitionseverywhere.Rotate
import com.transitionseverywhere.TransitionManager

class ActivitySplash : AppCompatActivity() {

    private val DurSplash = 2000L
    private val DurAnimation = 500L

    private val _splashHandler: Handler = Handler()
    private val _splashRunnable: Runnable = Runnable {
        startActivity(Intent(this, ActivityMain::class.java))
        overridePendingTransition(-1, -1)
        finish()
    }

    lateinit var _img: ImageView;
    lateinit var _pnlRoot: ViewGroup;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        _img = findViewById(R.id.img)
        _pnlRoot = findViewById(R.id.pnlRoot)
    }

    override fun onResume() {
        super.onResume()

        doAnimation()
        _splashHandler.postDelayed(_splashRunnable, DurSplash);
    }

    override fun onPause() {
        super.onPause()

        _splashHandler.removeCallbacks(_splashRunnable)
    }

    fun doAnimation() {
        _img.postDelayed({
            if (_img.isShown) {
                TransitionManager.beginDelayedTransition(_pnlRoot, Rotate())
                _img.rotation += 90
                doAnimation()
            }
        }, DurAnimation);
    }
}
