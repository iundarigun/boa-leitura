CREATE TABLE users
(
    id                     SERIAL PRIMARY KEY,
    username               VARCHAR(100) NOT NULL,
    encrypted_password     VARCHAR(100) NOT NULL,
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version                INTEGER NOT NULL
);