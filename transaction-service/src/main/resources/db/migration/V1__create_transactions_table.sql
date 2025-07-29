Create table transactions (
  id UUID PRIMARY KEY,
  sender_document_number VARCHAR(20) NOT NULL,
  receiver_document_number VARCHAR(20) NOT NULL,
  amount integer NOT NULL,
  transaction_status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);