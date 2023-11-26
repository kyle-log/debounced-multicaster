package com.cocomo.library.event

import com.cocomo.library.debounce.AlwaysThrowErrorHandler
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.event.AbstractApplicationEventMulticaster
import org.springframework.core.ResolvableType
import org.springframework.util.ErrorHandler

class CustomApplicationEventMulticaster(
    private val applicationEventProcessor: ApplicationEventProcessor,
    private val errorHandler: ErrorHandler = AlwaysThrowErrorHandler(),
) : AbstractApplicationEventMulticaster() {

    override fun multicastEvent(event: ApplicationEvent) {
        multicastEvent(event)
    }

    @Suppress("UNCHECKED_CAST")
    override fun multicastEvent(event: ApplicationEvent, eventType: ResolvableType?) {
        val type = eventType ?: ResolvableType.forInstance(event)
        for (aListener in getApplicationListeners(event, type)) {
            val listener = aListener as ApplicationListener<ApplicationEvent>
            runCatching { applicationEventProcessor.process(listener, event) }
                .getOrElse { errorHandler.handleError(it) }
        }
    }
}