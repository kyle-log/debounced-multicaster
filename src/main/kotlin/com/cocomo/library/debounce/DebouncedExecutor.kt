package com.cocomo.library.debounce

interface DebouncedExecutor {
    fun execute(event: DebouncedEvent, f: () -> Unit)
}
