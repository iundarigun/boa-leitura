CREATE TABLE saga_status
(
    id                         SERIAL PRIMARY KEY,
    saga_id                    BIGINT NOT NULL,
    status                     VARCHAR(100),
    created_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version                    INTEGER                     NOT NULL
);

ALTER TABLE ONLY saga_status
    ADD CONSTRAINT fk_saga_status_saga_id
        FOREIGN KEY (saga_id)
            REFERENCES saga (id);

INSERT INTO saga_status (saga_id, status, created_at, updated_at, version)
SELECT id, status, now(), now(), 1 FROM saga;