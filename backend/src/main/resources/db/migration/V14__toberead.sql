CREATE TABLE to_be_read
(
    id                     SERIAL PRIMARY KEY,
    position               BIGINT NOT NULL,
    done                   BOOLEAN NOT NULL DEFAULT FALSE,
    user_id                BIGINT NOT NULL,
    book_id                BIGINT NOT NULL,
    bought                 BOOLEAN NOT NULL DEFAULT FALSE,
    platforms              TEXT[],
    tags                   TEXT[],
    notes                  TEXT,
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version                INTEGER NOT NULL
);

ALTER TABLE ONLY to_be_read
    ADD CONSTRAINT fk_to_be_read_book_id
        FOREIGN KEY (book_id)
            REFERENCES book (id);
