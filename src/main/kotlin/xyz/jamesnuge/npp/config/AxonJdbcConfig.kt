package xyz.jamesnuge.npp.config

import org.axonframework.common.jdbc.ConnectionProvider
import org.axonframework.common.jdbc.DataSourceConnectionProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.config.SagaConfiguration
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore
import org.axonframework.eventhandling.tokenstore.jdbc.TokenSchema
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine
import org.axonframework.eventsourcing.eventstore.jdbc.PostgresEventTableFactory
import org.axonframework.modelling.saga.repository.SagaStore
import org.axonframework.modelling.saga.repository.jdbc.GenericSagaSqlSchema
import org.axonframework.modelling.saga.repository.jdbc.JdbcSagaStore
import org.axonframework.modelling.saga.repository.jdbc.PostgresSagaSqlSchema
import org.axonframework.serialization.Serializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class AxonJdbcConfig {

    @Bean
    open fun connectionProvider(dataSource: DataSource): ConnectionProvider {
        return DataSourceConnectionProvider(dataSource)
    }

    /**
     * Custom TokenStore configuration to avoid PostgreSQL BYTEA vs OID issues.
     *
     * PostgreSQL supports two binary storage mechanisms:
     * - BYTEA: inline binary data (expected by Axon)
     * - OID: large object reference (used via JDBC Blob API)
     *
     * Under certain conditions (e.g. when JPA/Hibernate is on the classpath),
     * the PostgreSQL JDBC driver may treat byte[] values as Large Objects and
     * send them using the Blob API (OID), instead of as raw bytes (BYTEA).
     *
     * This results in errors like:
     *   "column is of type bytea but expression is of type oid"
     *
     * By explicitly configuring Axon's JdbcTokenStore with a ConnectionProvider,
     * we ensure it uses plain JDBC (setBytes) instead of Blob handling,
     * preventing the driver from switching to OID-based storage.
     */
    @Bean
    open fun tokenStore(
        connectionProvider: ConnectionProvider,
        serializer: Serializer
    ): TokenStore {
        return JdbcTokenStore.builder()
            .connectionProvider(connectionProvider)
            .serializer(serializer)
            .schema(
                TokenSchema.builder()
                    .setTokenTable("token_entry")
                    .setProcessorNameColumn("processor_name")
                    .setSegmentColumn("segment")
                    .setTokenColumn("token")
                    .setTokenTypeColumn("token_type")
                    .setTimestampColumn("timestamp")
                    .setOwnerColumn("owner")
                    .build()
            )
            .build()
    }

    @Bean
    open fun eventStorageEngine(
        connectionProvider: ConnectionProvider,
        transactionManager: TransactionManager,
        serializer: Serializer
    ): EventStorageEngine {
        return JdbcEventStorageEngine.builder()
            .connectionProvider(connectionProvider)
            .transactionManager(transactionManager)
            .snapshotSerializer(serializer)
            .eventSerializer(serializer)
            .schema(
                EventSchema.builder()
                    .eventTable("domain_event_entry")
                    .globalIndexColumn("global_index")
                    .eventIdentifierColumn("event_identifier")
                    .aggregateIdentifierColumn("aggregate_identifier")
                    .sequenceNumberColumn("sequence_number")
                    .timestampColumn("time_stamp")
                    .metaDataColumn("meta_data")
                    .snapshotTable("snapshot_event_entry")
                    .payloadTypeColumn("payload_type")
                    .payloadRevisionColumn("payload_revision")
                    .build()
            )
            .build()
    }

    @Bean
    open fun sagaStore(
        connectionProvider: ConnectionProvider,
        serializer: Serializer
    ): SagaStore<Any> {
        return JdbcSagaStore.builder()
            .connectionProvider(connectionProvider)
            .serializer(serializer)
            .build()
    }
}