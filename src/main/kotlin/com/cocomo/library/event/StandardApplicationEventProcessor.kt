package com.cocomo.library.event

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener

class StandardApplicationEventProcessor : ApplicationEventProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun process(listener: ApplicationListener<*>, event: ApplicationEvent) {
        (listener as ApplicationListener<ApplicationEvent>).onApplicationEvent(event)
    }
}