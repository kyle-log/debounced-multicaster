package com.cocomo.library.amqp.rabbit

import org.springframework.amqp.core.Message

data class XDeathHeader(
    val count: Long = 0,
)

data class XDeathHeaders(
    val values: List<XDeathHeader>
) {

    fun counts(): Long = this.values.sumOf { it.count }

    companion object {
        fun of(message: Message): XDeathHeaders = message
            .messageProperties
            .let { it.xDeathHeader ?: emptyList() }
            .filter { it["reason"] == "rejected" }
            .map { XDeathHeader(it["count"] as Long) }
            .let { XDeathHeaders(it) }
    }
}