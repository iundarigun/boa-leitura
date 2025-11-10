ALTER TABLE users
    ADD COLUMN language_tags   JSONB,
    ADD COLUMN format_tags     JSONB,
    ADD COLUMN platform_tags   JSONB
