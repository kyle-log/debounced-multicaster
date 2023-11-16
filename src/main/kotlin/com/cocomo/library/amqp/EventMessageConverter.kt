package com.cocomo.library.amqp

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter

/**
 * @author arawn.park@daangn.com
 */
class EventMessageConverter(
    val defaultExchange: String = "amq.fanout",
) {

    private val messageConverter: MessageConverter = Jackson2JsonMessageConverter(
        jacksonMapperBuilder().addModule(JavaTimeModule()).build()
    )

    fun <E : Any> toMessage(
        event: E,
        exchange: String = defaultExchange,
        routingKey: String = "",
    ): DestinationMessage {
        val messageProperties = MessagePropertiesBuilder.newInstance().build()
        val message = messageConverter.toMessage(event, messageProperties)

        return DestinationMessage(
            exchange = exchange,
            routingKey = routingKey,
            content = message
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <E : Any> fromMessage(message: Message): E {
        return messageConverter.fromMessage(message) as E
    }

    data class DestinationMessage(
        val exchange: String,
        val routingKey: String,
        val content: Message
    ) {

        val properties: MessageProperties
            get() = content.messageProperties
    }
}
