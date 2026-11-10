CREATE TABLE card (
    id          UUID            PRIMARY KEY,
    number      VARCHAR(16)     NOT NULL UNIQUE,
    holder_name VARCHAR(100)    NOT NULL,
    expiration  DATE            NOT NULL,
    status      VARCHAR(20)     NOT NULL,
    CONSTRAINT card_status_check    CHECK ( status IN ('ACTIVE', 'BLOCKED', 'CANCELED'))
);
