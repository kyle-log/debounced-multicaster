package com.cocomo.library.event

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener

class StandardApplicationEventProcessor : ApplicationEventProcessor {

    override fun process(listener: ApplicationListener<ApplicationEvent>, event: ApplicationEvent) {
        listener.onApplicationEvent(event)
    }
}