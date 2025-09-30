CREATE TABLE book
(
    id                SERIAL PRIMARY KEY,
    title             VARCHAR(255) NOT NULL,
    goodreads_id      BIGINT       NOT NULL,
    isbn              VARCHAR(100),
    isbn13            VARCHAR(100),
    number_of_pages   INT,
    publisher_year    INT          NOT NULL,
    original_language VARCHAR(100),
    author_id         BIGINT       NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version           INTEGER      NOT NULL
);

CREATE UNIQUE INDEX BOOK_IDX1 ON book (goodreads_id);

ALTER TABLE ONLY book
    ADD CONSTRAINT fk_book_author_id
    FOREIGN KEY (author_id)
    REFERENCES author(id);

