CREATE UNIQUE INDEX idx_accounts_email
    ON accounts (email);

CREATE UNIQUE INDEX idx_accounts_document_number
    ON accounts (document_number);