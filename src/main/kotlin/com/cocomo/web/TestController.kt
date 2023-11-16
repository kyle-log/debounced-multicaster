package com.cocomo.web

import com.cocomo.library.event.Event1Sec
import com.cocomo.library.event.Event5Sec
import com.cocomo.library.event.EventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    val eventPublisher: EventPublisher,
) {

    @GetMapping("/a")
    fun test1() {
        eventPublisher.publish(Event1Sec())
    }

    @GetMapping("/b/b")
    fun test5() {
        eventPublisher.publish(Event5Sec())
    }
}