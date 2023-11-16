package com.cocomo.library.event

import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener

class StandardApplicationEventProcessor : ApplicationEventProcessor {
    override fun process(listener: GenericApplicationListener, event: ApplicationEvent) {
        println("on application event")
        listener.onApplicationEvent(event)
    }
}