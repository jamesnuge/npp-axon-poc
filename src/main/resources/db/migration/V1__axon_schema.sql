-- =========================================================
-- AXON FRAMEWORK JDBC SCHEMA (PostgreSQL)
-- Covers:
-- - Event Store
-- - Tracking Processors
-- - Sagas
-- - Snapshots
-- =========================================================

-- =========================================================
-- EVENT STORE
-- =========================================================

CREATE TABLE IF NOT EXISTS domain_event_entry (
                                                  global_index BIGSERIAL PRIMARY KEY,
                                                  event_identifier VARCHAR(255) NOT NULL,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255),
    event_type VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255),
    payload BYTEA NOT NULL,
    meta_data BYTEA,
    time_stamp VARCHAR(255) NOT NULL
    );

CREATE UNIQUE INDEX IF NOT EXISTS idx_aggregate_seq
    ON domain_event_entry (aggregate_identifier, sequence_number);

CREATE INDEX IF NOT EXISTS idx_global_index
    ON domain_event_entry (global_index);


-- =========================================================
-- SNAPSHOT STORE (OPTIONAL BUT RECOMMENDED)
-- =========================================================

CREATE TABLE IF NOT EXISTS snapshot_event_entry (
                                                    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255),
    payload BYTEA NOT NULL,
    meta_data BYTEA,
    time_stamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_identifier, sequence_number)
    );


-- =========================================================
-- TRACKING PROCESSORS (PROJECTIONS / EVENT HANDLERS / SAGAS)
-- =========================================================

CREATE TABLE IF NOT EXISTS token_entry (
                                           processor_name VARCHAR(255) NOT NULL,
    segment INTEGER NOT NULL,
    owner VARCHAR(255),
    timestamp VARCHAR(255) NOT NULL,
    token BYTEA,
    token_type VARCHAR(255),
    PRIMARY KEY (processor_name, segment)
    );


-- =========================================================
-- SAGAS
-- =========================================================

CREATE TABLE IF NOT EXISTS saga_entry (
                                          saga_id VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    saga_type VARCHAR(255) NOT NULL,
    serialized_saga BYTEA,
    PRIMARY KEY (saga_id)
    );

CREATE INDEX IF NOT EXISTS idx_saga_type
    ON saga_entry (saga_type);


-- =========================================================
-- SAGA ASSOCIATIONS (LOOKUP / ROUTING)
-- =========================================================

CREATE TABLE IF NOT EXISTS association_value_entry (
                                                       id BIGSERIAL PRIMARY KEY,
                                                       association_key VARCHAR(255) NOT NULL,
    association_value VARCHAR(255),
    saga_type VARCHAR(255) NOT NULL,
    saga_id VARCHAR(255) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_assoc_value
    ON association_value_entry (association_key, association_value);

CREATE INDEX IF NOT EXISTS idx_assoc_saga
    ON association_value_entry (saga_type, saga_id);