package com.cocomo

import com.cocomo.library.amqp.rabbit.RabbitEventProcessor
import com.cocomo.library.debounce.DebouncedApplicationEventProcessor
import com.cocomo.library.debounce.DebouncedExecutor
import com.cocomo.library.debounce.StandardDebouncedExecutor
import com.cocomo.library.event.*
import com.cocomo.worker.EventHandler
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisOperations

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class Configuration {

    // You can change cacheManager to redis or something
    @Bean
    fun debouncedExecutor(
        eventPublisher: EventPublisher,
        redis: RedisOperations<String, String>,
    ) = StandardDebouncedExecutor(
        eventPublisher = eventPublisher,
        redis = redis,
    )

    @Bean
    fun applicationEventProcessor(
        debouncedExecutor: DebouncedExecutor,
    ) = StandardApplicationEventProcessor()
        .decoratedBy { DebouncedApplicationEventProcessor(it, debouncedExecutor) }

    // Do not change bean name
    @Bean("applicationEventMulticaster")
    fun customApplicationEventMulticaster(
        applicationEventProcessor: ApplicationEventProcessor,
    ) = CustomApplicationEventMulticaster(
        applicationEventProcessor = applicationEventProcessor,
    )

    @Bean
    fun eventHandler() = EventHandler()

    @Bean
    fun rabbitEventProcessor(
        connectionFactory: ConnectionFactory,
        applicationEventPublisher: ApplicationEventPublisher,
        environment: Environment
    ) = RabbitEventProcessor.create(
        connectionFactory = connectionFactory,
        applicationEventPublisher = applicationEventPublisher,
    )

    @Bean
    fun redisConnectionFactory(
        builderCustomizers: ObjectProvider<LettuceClientConfigurationBuilderCustomizer>,
        environment: Environment
    ): LettuceConnectionFactory {
        val primaryHost = "localhost"
        val primaryPort = 6379

        val clusterConfiguration = RedisStaticMasterReplicaConfiguration(primaryHost, primaryPort)
        val clientConfigBuilder: LettuceClientConfiguration.LettuceClientConfigurationBuilder = LettuceClientConfiguration.builder()

        builderCustomizers.orderedStream().forEach { customizer -> customizer.customize(clientConfigBuilder) }
        return LettuceConnectionFactory(clusterConfiguration, clientConfigBuilder.build())
    }
}
