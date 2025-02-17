CREATE TABLE subscribers (
  uuid UUID NOT NULL,
   telegram_id VARCHAR(255),
   price DOUBLE PRECISION,
   CONSTRAINT pk_subscribers PRIMARY KEY (uuid)
);