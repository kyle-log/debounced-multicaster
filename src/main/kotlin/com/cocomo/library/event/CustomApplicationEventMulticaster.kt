package com.cocomo.library.event

import com.cocomo.library.debounce.AlwaysThrowErrorHandler
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.AbstractApplicationEventMulticaster
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType
import org.springframework.util.ErrorHandler

class CustomApplicationEventMulticaster(
    private val applicationEventProcessor: ApplicationEventProcessor,
    private val errorHandler: ErrorHandler = AlwaysThrowErrorHandler(),
) : AbstractApplicationEventMulticaster() {

    override fun multicastEvent(event: ApplicationEvent) {
        multicastEvent(event)
    }

    override fun multicastEvent(event: ApplicationEvent, eventType: ResolvableType?) {
        val type = eventType ?: ResolvableType.forInstance(event)
        for (listener in getApplicationListeners(event, type)) {
            if (listener !is GenericApplicationListener) {
                println("No generic application listener")
                continue
            }
            runCatching { applicationEventProcessor.process(listener, event) }
                .getOrElse { errorHandler.handleError(it) }
        }
    }
}