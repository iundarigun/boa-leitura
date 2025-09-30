CREATE TABLE reading
(
    id         SERIAL PRIMARY KEY,
    my_rating  INT,
    date_read  DATE                        NOT NULL,
    format     VARCHAR(100),
    language   VARCHAR(100),
    book_id    BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version    INTEGER                     NOT NULL
);

ALTER TABLE ONLY reading
    ADD CONSTRAINT fk_reading_book_id
        FOREIGN KEY (book_id)
            REFERENCES book (id);

