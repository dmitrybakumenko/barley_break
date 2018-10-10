package com.bakumenko.barleybreak.game

import com.bakumenko.barleybreak.data.preferences.property.GenericPrefProperty

enum class EGrid(val id: Int, val rows: Int, val columns: Int) : GenericPrefProperty.IPrefProperty<Int, EGrid> {
    G4x4(0, 4, 4),
    G5x5(1, 5, 5),
    G6x6(2, 6, 6);

    override fun toSimple(value: EGrid) = value.id
    override fun fromSimple(simpleValue: Int) = fromId(simpleValue)

    companion object

    fun fromId(id: Int): EGrid = EGrid.values().find { it.id == id }
            ?: throw IllegalArgumentException("Can't find EGrid by id: $id at EGrid.fromId")
}
