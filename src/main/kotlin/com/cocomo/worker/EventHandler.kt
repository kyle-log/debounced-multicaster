package com.cocomo.worker

import com.cocomo.library.event.ArticleLikeCountChangedEvent
import org.springframework.context.event.EventListener

class EventHandler {

    @EventListener
    fun handle(event: ArticleLikeCountChangedEvent) {
        println("Handled. $event")
    }
}