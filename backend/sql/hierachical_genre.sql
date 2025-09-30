/*************************/
/* Example joining books */
/*************************/
WITH RECURSIVE genre_hierarchy AS (
    SELECT g.id, g.parent_genre_id, g.name, g.id AS start_id
    FROM genre g
    UNION ALL
    SELECT g.id, g.parent_genre_id, g.name, gh.start_id
    FROM genre g
             JOIN genre_hierarchy gh ON
        g.id = gh.parent_genre_id
)
SELECT b.id AS book_id,
       b.title,
       b.genre_id,
       gh_root.id AS root_genre_id,
       gh_root.name
FROM book b
         LEFT JOIN genre_hierarchy gh_root
                   ON b.genre_id = gh_root.start_id
WHERE gh_root.parent_genre_id IS NULL
  AND b.genre_id IS NOT NULL;