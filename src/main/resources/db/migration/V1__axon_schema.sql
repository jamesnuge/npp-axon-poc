-- =========================================================
-- AXON FRAMEWORK JDBC SCHEMA (PostgreSQL)
-- =========================================================

-- =========================================================
-- SEQUENCES
-- =========================================================

CREATE SEQUENCE IF NOT EXISTS domainevententryseq;

-- =========================================================
-- EVENT STORE
-- =========================================================

CREATE TABLE IF NOT EXISTS domainevententry (
    globalindex BIGINT NOT NULL
    DEFAULT nextval('domainevententryseq'),
    eventidentifier VARCHAR(255) NOT NULL,
    aggregateidentifier VARCHAR(255) NOT NULL,
    sequencenumber BIGINT NOT NULL,
    type VARCHAR(255),
    revision VARCHAR(255),
    payloadtype VARCHAR(255) NOT NULL,
    payloadrevision VARCHAR(255),
    payload BYTEA NOT NULL,
    metadata BYTEA,
    timestamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (globalindex)
    );

CREATE UNIQUE INDEX IF NOT EXISTS idx_aggregate_seq
    ON domainevententry (aggregateidentifier, sequencenumber);

CREATE INDEX IF NOT EXISTS idx_global_index
    ON domainevententry (globalindex);


-- =========================================================
-- SNAPSHOT STORE
-- =========================================================

CREATE TABLE IF NOT EXISTS snapshotevententry (
    aggregateidentifier VARCHAR(255) NOT NULL,
    sequencenumber BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    eventtype VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    payloadtype VARCHAR(255) NOT NULL,
    payloadrevision VARCHAR(255),
    payload BYTEA NOT NULL,
    metadata BYTEA,
    timestamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregateidentifier, sequencenumber)
    );


-- =========================================================
-- TOKEN STORE
-- =========================================================

CREATE TABLE IF NOT EXISTS tokenentry (
    processorname VARCHAR(255) NOT NULL,
    segment INTEGER NOT NULL,
    owner VARCHAR(255),
    timestamp VARCHAR(255) NOT NULL,
    token BYTEA,
    tokentype VARCHAR(255),
    PRIMARY KEY (processorname, segment)
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