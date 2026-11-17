CREATE TABLE credit_account (
    id                  UUID            PRIMARY KEY,
    -- Sem chave estrangeira: a tabela de cartões fica no banco do card-service.
    card_id             UUID            NOT NULL UNIQUE,
    credit_limit        NUMERIC(15,2)   NOT NULL,
    available_credit    NUMERIC(15,2)   NOT NULL,
    CONSTRAINT credit_account_credit_limit_check CHECK ( credit_limit > 0 ),
    CONSTRAINT credit_account_available_credit_check CHECK ( available_credit >= 0 AND available_credit <= credit_limit)
);
