package com.cocomo.library.debounce

interface DebouncedEvent {
    val debounceKey: String
    val debounceTtlMillis: Long
    val debounced: Boolean

    fun debounce(): DebouncedEvent
}
