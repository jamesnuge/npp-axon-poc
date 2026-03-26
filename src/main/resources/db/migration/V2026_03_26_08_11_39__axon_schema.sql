CREATE TABLE IF NOT EXISTS payment_audit(
    payment_id UUID PRIMARY KEY,
    amount DECIMAL NOT NULL,
    payer_id VARCHAR(255) NOT NULL,
    payee_id VARCHAR(255) NOT NULL,
    payment_type VARCHAR(255) NOT NULL,
    origin VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL
)