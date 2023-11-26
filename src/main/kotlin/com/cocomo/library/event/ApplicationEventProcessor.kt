package com.cocomo.library.event

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener

interface ApplicationEventProcessor {
    fun process(listener: ApplicationListener<ApplicationEvent>, event: ApplicationEvent)
}

fun ApplicationEventProcessor.decoratedBy(
    decorator: (ApplicationEventProcessor) -> ApplicationEventProcessor
): ApplicationEventProcessor = decorator(this)