/**
  Sagues principals finalitzades
 */
select name from saga where
    total_main_titles = (select count(*) from book where saga_id = saga.id and saga_main_title is true);



/**
  Sagues principals + complementaries finalitzades
 */
select name from saga where
      total_complementary_titles > 0 and
    (total_main_titles + total_complementary_titles) = (select count(*) from book where saga_id = saga.id);


/**
  Sagues principals finalitzades però amb algun complementari no acabat
 */
select name from saga where
      total_main_titles = (select count(*) from book where saga_id = saga.id and saga_main_title is true)
      and (total_main_titles + total_complementary_titles) != (select count(*) from book where saga_id = saga.id);

/**
  Sagues principals no finalitzades
 */
select name, status from saga
            where total_main_titles > (select count(*) from book where saga_id = saga.id and saga_main_title is true)
            and status != 'dnf' and total_main_titles  = 1 +
                                    (select count(*) from book where saga_id = saga.id and saga_main_title is true);

/**
  Sagues iniciades el 2025
 */
SELECT s.name, ss.status FROM reading r
INNER JOIN book b ON
    r.book_id = b.id
INNER JOIN public.saga s on
    s.id = b.saga_id
LEFT JOIN saga_status ss on
    s.id = ss.saga_id AND ss.user_id = 1
WHERE
    saga_order = 1 AND
    r.user_id = 1 AND
    b.saga_main_title IS TRUE          AND
    date_part('year', r.date_read) = 2025 AND
    0 = (select count(*) from reading r1 WHERE r1.book_id = r.book_id AND date_part('year', r1.date_read) < 2025)

union

/**
  Sagues finalitzades el 2025
 */
SELECT s.name, ss.status FROM reading r
INNER JOIN book b ON
    r.book_id = b.id
INNER JOIN public.saga s on s.id = b.saga_id
LEFT JOIN saga_status ss on
    s.id = ss.saga_id AND ss.user_id = 1
WHERE
    r.user_id = 1                      AND
    b.saga_order = s.total_main_titles AND
    b.saga_main_title IS TRUE          AND
    date_part('year', r.date_read) = 2025;

/**
  Sagues principals iniciades finalitzades el 2025
 */
select * from saga
where 0 < (select count(1) from book
     inner join public.reading r on book.id = r.book_id
     where saga_main_title is true and
          saga_id = saga.id and
          saga_order = 1 and
        date_part('year', date_read) = 2025) and
    0 < (select count(1) from book
     inner join public.reading r on book.id = r.book_id
     where saga_main_title is true and
         saga_id = saga.id and
         cast(saga_order as integer) = saga.total_main_titles and
         date_part('year', date_read) = 2025);

