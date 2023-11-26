package com.cocomo.library.debounce

import com.cocomo.library.event.ApplicationEventProcessor
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.PayloadApplicationEvent
import org.springframework.context.event.GenericApplicationListener

class DebouncedApplicationEventProcessor(
    private val delegate: ApplicationEventProcessor,
    private val debouncedExecutor: DebouncedExecutor,
) : ApplicationEventProcessor {

    override fun process(listener: ApplicationListener<ApplicationEvent>, event: ApplicationEvent) {
        if (listener !is GenericApplicationListener) {
            delegate.process(listener, event)
            return
        }
        if (event !is PayloadApplicationEvent<*>) {
            delegate.process(listener, event)
            return
        }
        when (val debouncedEvent = event.payload) {
            is DebouncedEvent -> debouncedExecutor.execute(debouncedEvent) {
                delegate.process(listener, event)
            }
            else -> delegate.process(listener, event)
        }
    }
}