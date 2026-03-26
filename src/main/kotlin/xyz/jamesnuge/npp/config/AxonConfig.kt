package xyz.jamesnuge.npp.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.axonframework.common.jdbc.PersistenceExceptionResolver
import org.axonframework.eventsourcing.eventstore.jpa.SQLStateResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.context.annotation.Primary


@Configuration
open class AxonConfig {

    @Bean
    open fun persistenceExceptionResolver(): PersistenceExceptionResolver = SQLStateResolver()

    @Primary
    @Bean
    open fun serializer(): Serializer {
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())

        return JacksonSerializer.builder()
            .objectMapper(objectMapper)
            .build()
    }

}