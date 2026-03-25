package xyz.jamesnuge.npp.config

import org.axonframework.common.jdbc.PersistenceExceptionResolver
import org.axonframework.eventsourcing.eventstore.jpa.SQLStateResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {

    @Bean
    fun persistenceExceptionResolver(): PersistenceExceptionResolver = SQLStateResolver()
}