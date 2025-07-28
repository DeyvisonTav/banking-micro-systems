ALTER TABLE accounts
    ADD CONSTRAINT uq_document_type_number UNIQUE (document_type, document_number);