-- Add updated_at column
ALTER TABLE transactions ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Update amount column to use DECIMAL for better precision
ALTER TABLE transactions ALTER COLUMN amount TYPE DECIMAL(19,2);

-- Update status values to match new enum
UPDATE transactions SET transaction_status = 'COMPLETED' WHERE transaction_status = 'SUCCESS';
UPDATE transactions SET transaction_status = 'FAILED' WHERE transaction_status = 'ERROR'; 