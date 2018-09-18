package com.bakumenko.barleybreak.helpers.kt_singletone

open class SingletonHolder<out T, in A1, in A2>(creator: (A1, A2) -> T) {
    private var _creator: ((A1, A2) -> T)? = creator

    @Volatile
    private var _instance: T? = null

    fun getInstance(a1: A1, a2: A2): T {
        val i = _instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = _instance
            if (i2 != null) {
                i2
            } else {
                val created = _creator!!(a1, a2)
                _instance = created
                _creator = null
                created
            }
        }
    }
}