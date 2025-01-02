CREATE TABLE author
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    gender        VARCHAR(10),
    nationality   VARCHAR(100),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version       INTEGER NOT NULL
);