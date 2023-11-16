package com.cocomo.library.debounce

import com.cocomo.library.event.Millis

interface DebouncedExecutor {
    fun execute(key: String, ttl: Millis, f: () -> Unit)
}
