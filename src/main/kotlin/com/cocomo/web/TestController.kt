package com.cocomo.web

import com.cocomo.library.event.ArticleLikeCountChangedEvent
import com.cocomo.library.event.EventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    val eventPublisher: EventPublisher,
) {

    @GetMapping("articles/{id}/ttl/{ttl}")
    fun test(
        @PathVariable("id") id: Long,
        @PathVariable("ttl") ttl: Long,
    ) {
        val event = ArticleLikeCountChangedEvent(
            articleId = id,
            debounceTtlMillis = ttl,
        )
        eventPublisher.publish(event)
    }
}