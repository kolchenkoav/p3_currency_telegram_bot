CREATE SCHEMA IF NOT EXISTS telegram_bot_schema
CREATE TABLE subscribers (
  uuid UUID NOT NULL,
   telegram_id BIGINT,
   price DOUBLE PRECISION,
   CONSTRAINT pk_subscribers PRIMARY KEY (uuid)
);