package com.cocomo.library.event

import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener

interface ApplicationEventProcessor {
    fun process(listener: GenericApplicationListener, event: ApplicationEvent)
}

fun ApplicationEventProcessor.decoratedBy(
    decorator: (ApplicationEventProcessor) -> ApplicationEventProcessor
): ApplicationEventProcessor = decorator(this)