ALTER TABLE book
    DROP COLUMN isbn13;

DROP INDEX BOOK_IDX1;

ALTER TABLE book
    ALTER COLUMN goodreads_id DROP NOT NULL;

ALTER TABLE book
    ADD COLUMN url_image       VARCHAR(512),
    ADD COLUMN url_image_small VARCHAR(512),
    ADD COLUMN language        VARCHAR(100),
    ADD COLUMN original_title  VARCHAR(255);
