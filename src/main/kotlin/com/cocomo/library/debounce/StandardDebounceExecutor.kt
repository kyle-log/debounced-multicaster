package com.cocomo.library.debounce

import com.cocomo.library.event.EventPublisher
import com.cocomo.library.event.Millis
import org.springframework.data.redis.core.RedisOperations
import java.time.Duration
import java.util.concurrent.TimeUnit

class StandardDebouncedExecutor(
    val eventPublisher: EventPublisher,
    val redis: RedisOperations<String, String>,
) : DebouncedExecutor {

    private val ops = redis.opsForValue()

    override fun execute(key: String, ttl: Millis, f: () -> Unit) {
        if (alreadyDebounced(key)) {
            println("Skipped. key=$key")
        } else {
            println("Debounce. key=$key")
            f()
            ops.set(key, "1", ttl.value, TimeUnit.MILLISECONDS)
            eventPublisher.publishLazy(key, ttl)
        }
    }

    private fun alreadyDebounced(key: String): Boolean {
        return runCatching { ops.get(key) }.getOrNull() != null
    }
}
