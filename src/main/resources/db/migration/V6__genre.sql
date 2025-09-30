CREATE TABLE genre
(
    id                         SERIAL PRIMARY KEY,
    name                       VARCHAR(100)     NOT NULL,
    parent_genre_id            BIGINT,
    created_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version                    INTEGER                     NOT NULL
);

ALTER TABLE book
    ADD COLUMN genre_id        BIGINT;

ALTER TABLE ONLY book
    ADD CONSTRAINT fk_book_genre_id
        FOREIGN KEY (genre_id)
            REFERENCES genre (id);
