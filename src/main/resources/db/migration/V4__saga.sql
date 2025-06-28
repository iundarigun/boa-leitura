CREATE TABLE saga
(
    id                         SERIAL PRIMARY KEY,
    name                       VARCHAR(100)     NOT NULL,
    total_main_titles          INT,
    total_complementary_titles INT,
    concluded                  BOOLEAN,
    created_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version                    INTEGER                     NOT NULL
);

ALTER TABLE book
    ADD COLUMN saga_id         BIGINT,
    ADD COLUMN saga_order      DECIMAL,
    ADD COLUMN saga_main_title BOOLEAN;

ALTER TABLE ONLY book
    ADD CONSTRAINT fk_book_saga_id
        FOREIGN KEY (saga_id)
            REFERENCES saga (id);

