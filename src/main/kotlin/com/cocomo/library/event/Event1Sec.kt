package com.cocomo.library.event

import com.cocomo.library.debounce.DebouncedEvent
import java.util.*

data class Event1Sec(
    val value: String = "success",
    override val debounceKey: String = UUID.randomUUID().toString(),
    override val debounceTtlMillis: Long = 1000L
) : DebouncedEvent