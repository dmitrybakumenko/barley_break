package com.bakumenko.barleybreak.data.preferences.property

import android.content.SharedPreferences

abstract class BasePrefProperty<T : Any>(val prefs: SharedPreferences,
                                         val key: String,
                                         val defValue: T) {

    private var fValue: T? = null
    private var value: T
        get() {
            if (fValue == null) {
                fValue = read()
            }
            return fValue as T
        }
        set(value) {
            fValue = value
            write(value)
        }

    abstract fun read(): T
    abstract fun write(newValue: T)
}

class BooleanPrefProperty(prefs: SharedPreferences,
                          key: String,
                          defValue: Boolean) : BasePrefProperty<Boolean>(prefs, key, defValue) {

    override fun write(newValue: Boolean) {
        prefs.edit().putBoolean(key, newValue).apply()
    }

    override fun read(): Boolean {
        return prefs.getBoolean(key, defValue)
    }
}

class IntPrefProperty(prefs: SharedPreferences,
                      key: String,
                      defValue: Int) : BasePrefProperty<Int>(prefs, key, defValue) {

    override fun write(newValue: Int) {
        prefs.edit().putInt(key, newValue).apply()
    }

    override fun read(): Int {
        return prefs.getInt(key, defValue)
    }
}

class LongPrefProperty(prefs: SharedPreferences,
                       key: String,
                       defValue: Long) : BasePrefProperty<Long>(prefs, key, defValue) {

    override fun write(newValue: Long) {
        prefs.edit().putLong(key, newValue).apply()
    }

    override fun read(): Long {
        return prefs.getLong(key, defValue)
    }
}

class StringPrefProperty(prefs: SharedPreferences,
                         key: String,
                         defValue: String) : BasePrefProperty<String>(prefs, key, defValue) {

    override fun write(newValue: String) {
        prefs.edit().putString(key, newValue).apply()
    }

    override fun read(): String {
        return prefs.getString(key, defValue) ?: defValue
    }
}

class GenericPrefProperty<T : GenericPrefProperty.IPrefProperty<V, T>, V : Any>(defValue: T, private val reader: BasePrefProperty<V>) : BasePrefProperty<T>(reader.prefs, reader.key, defValue) {
    override fun write(newValue: T) {
        reader.write(newValue.toSimple(newValue))
    }

    override fun read(): T {
        return defValue.fromSimple(reader.read())
    }

    interface IPrefProperty<V, T> {
        fun fromSimple(simpleValue: V): T
        fun toSimple(value: T): V
    }
}