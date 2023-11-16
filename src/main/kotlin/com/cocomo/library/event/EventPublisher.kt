package com.cocomo.library.event

interface EventPublisher {
    fun <E : Any> publish(event: E)
    fun <E : Any> publishLazy(event: E, ttl: Millis = Millis.ZERO)
}

@JvmInline
value class Millis private constructor(val value: Long) {

    fun isNone(): Boolean = this == ZERO

    companion object {
        val ZERO = Millis(0)

        fun of(value: Long): Millis {
            require(value >= 0) { "TTL must be positive" }
            return Millis(value)
        }
    }
}