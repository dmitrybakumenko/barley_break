package com.bakumenko.barleybreak.ui.activities.main

import android.os.Bundle
import com.bakumenko.barleybreak.R
import com.bakumenko.barleybreak.ui.activities.ActivityBase

class ActivityMain : ActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}