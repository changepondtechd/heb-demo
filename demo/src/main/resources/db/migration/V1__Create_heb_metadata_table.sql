-- Migration: Create heb_metadata table
-- Version: 1
-- Purpose: Create metadata table to store field configurations

CREATE TABLE heb_metadata (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    fieldname NVARCHAR(255) NOT NULL,
    fieldvalue NVARCHAR(MAX) NOT NULL,
    active BIT NOT NULL DEFAULT 1,
    ordernumber INT NOT NULL DEFAULT 0
);

-- Create index on fieldname for faster lookups
CREATE INDEX idx_heb_metadata_fieldname ON heb_metadata(fieldname);
