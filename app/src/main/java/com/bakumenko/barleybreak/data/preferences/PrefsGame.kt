package com.bakumenko.barleybreak.data.preferences

import android.content.Context
import com.bakumenko.barleybreak.data.preferences.property.BooleanPrefProperty
import com.bakumenko.barleybreak.data.preferences.property.GenericPrefProperty
import com.bakumenko.barleybreak.data.preferences.property.IntPrefProperty
import com.bakumenko.barleybreak.game.EGrid

class PrefsGame(context: Context) : PrefsBase("settingsGame", context) {
    private var grid = GenericPrefProperty(EGrid.G4x4, IntPrefProperty(prefs, "g", EGrid.G4x4.id))
    private val vibration = BooleanPrefProperty(prefs, "v", true)
}