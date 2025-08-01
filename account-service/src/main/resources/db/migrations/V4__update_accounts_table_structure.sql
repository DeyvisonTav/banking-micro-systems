-- Add created_at and updated_at columns
ALTER TABLE accounts ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE accounts ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Update balance column to use DECIMAL for better precision
ALTER TABLE accounts ALTER COLUMN balance TYPE DECIMAL(19,2);

-- Add NOT NULL constraints to existing columns that should be required
ALTER TABLE accounts ALTER COLUMN document_type SET NOT NULL;
ALTER TABLE accounts ALTER COLUMN document_number SET NOT NULL; 