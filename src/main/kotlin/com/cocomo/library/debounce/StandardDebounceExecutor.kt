package com.cocomo.library.debounce

import com.cocomo.library.event.EventPublisher
import com.cocomo.library.event.Millis
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.RedisOperations
import java.time.Duration

class StandardDebouncedExecutor(
    val eventPublisher: EventPublisher,
    val redis: RedisOperations<String, String>,
) : DebouncedExecutor {

    private val ops = redis.opsForValue()

    override fun execute(event: DebouncedEvent, f: () -> Unit) {
        when {
            event.debounceFinished() -> f()
            event.inDebounce() -> skip()
                .also { println("Skipped. ${event.debounceKey}") }
            else -> startDebounce(event)
                .also { println("Start. ${event.debounceKey}") }
        }
    }

    private fun startDebounce(event: DebouncedEvent) {
        ops.set(event.debounceKey, ANY_VALUE, Duration.ofMillis(event.debounceTtlMillis))
        eventPublisher.publishLazy(event.debounce(), Millis.of(event.debounceTtlMillis))
    }

    private fun DebouncedEvent.debounceFinished(): Boolean = this.debounced

    private fun DebouncedEvent.inDebounce(): Boolean = runCatching {
        ops.get(this.debounceKey)
    }.getOrNull() != null

    companion object {
        private const val ANY_VALUE: String = "1"
        private fun skip() = Unit
    }
}
