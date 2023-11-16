package com.cocomo.library.debounce

import com.cocomo.library.event.ApplicationEventProcessor
import com.cocomo.library.event.Millis
import org.springframework.context.ApplicationEvent
import org.springframework.context.PayloadApplicationEvent
import org.springframework.context.event.GenericApplicationListener

class DebouncedApplicationEventProcessor(
    private val delegate: ApplicationEventProcessor,
    private val debouncedExecutor: DebouncedExecutor,
) : ApplicationEventProcessor {

    override fun process(listener: GenericApplicationListener, event: ApplicationEvent) {
        if (event !is PayloadApplicationEvent<*>) {
            println("No payload application event")
            return delegate.process(listener, event)
        }
        val payload = event.payload
        if (payload !is DebouncedEvent) {
            println("No debounced event")
            return delegate.process(listener, event)

        }
        debouncedExecutor.execute(
            key = payload.debounceKey,
            ttl = Millis.of(payload.debounceTtlMillis)
        ) {
            delegate.process(listener, event)
        }
    }
}