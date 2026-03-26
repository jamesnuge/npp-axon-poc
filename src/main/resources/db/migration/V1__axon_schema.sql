-- =========================================================
-- AXON FRAMEWORK JDBC SCHEMA (PostgreSQL)
-- =========================================================

-- =========================================================
-- SEQUENCES
-- =========================================================

CREATE SEQUENCE IF NOT EXISTS domain_event_entry_seq;

-- =========================================================
-- EVENT STORE
-- =========================================================

CREATE TABLE IF NOT EXISTS domain_event_entry (
    global_index BIGINT NOT NULL
    DEFAULT nextval('domain_event_entry_seq'),
    event_identifier VARCHAR(255) NOT NULL,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255),
    revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255),
    payload BYTEA NOT NULL,
    meta_data BYTEA,
    time_stamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (global_index)
    );

CREATE UNIQUE INDEX IF NOT EXISTS idx_aggregate_seq
    ON domain_event_entry (aggregate_identifier, sequence_number);

CREATE INDEX IF NOT EXISTS idx_global_index
    ON domain_event_entry (global_index);


-- =========================================================
-- SNAPSHOT STORE
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
-- TOKEN STORE
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

CREATE TABLE IF NOT EXISTS sagaentry (
    sagaid VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    sagatype VARCHAR(255) NOT NULL,
    serializedsaga BYTEA,
    PRIMARY KEY (sagaid)
    );

CREATE TABLE IF NOT EXISTS associationvalueentry (
    id BIGSERIAL PRIMARY KEY,
    associationkey VARCHAR(255) NOT NULL,
    associationvalue VARCHAR(255),
    sagatype VARCHAR(255) NOT NULL,
    sagaid VARCHAR(255) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_assoc_value
    ON associationvalueentry (associationkey, associationvalue);

CREATE INDEX IF NOT EXISTS idx_assoc_saga
    ON associationvalueentry (sagatype, sagaid);