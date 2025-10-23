

/***************************/
/***************************/
/*******  LANGUAGE *********/
/***************************/
/***************************/
/* versio original vs traduida */
select case when b.original_language = r.language then 'original' else 'translated' end as version, count(*)
from book b
     inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by b.original_language = r.language;

/* Traduits */
select r.language, count(*)
from book b
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
  and b.original_language != r.language
group by r.language;

/* originals */
select r.language, count(*)
from book b
     inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
  and b.original_language = r.language
group by r.language;

/* Total per idioma */
select r.language, count(*)
from book b
     inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by r.language;

/* pagines per idioma */
select r.language, sum(b.number_of_pages)
from book b
    inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by r.language;

/***************************/
/***************************/
/*******  AUTHORS  *********/
/***************************/
/***************************/
/* FEMALE / MALE */
select date_part('year', date_read), a.gender, count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) >= 2023
group by date_part('year', date_read), a.gender
order by 1;

/* Total */
select a.name, count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by a.name
order by 2 desc limit 5;


/*New authors*/
select count (distinct a.name)
from book b
     inner join author a on a.id = b.author_id
     inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
  and a.id not in (select a1.id
       from author a1
        inner join book b1 on b1.author_id = a1.id
        inner join public.reading r1 on b1.id = r1.book_id
       where date_part('year', date_read) < 2025);


/***************************/
/***************************/
/******  PLATFORM  *********/
/***************************/
/***************************/
select r.format, count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by r.format;

select r.platform, count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by r.platform;

/***************************/
/***************************/
/********  SIZE  ***********/
/***************************/
/***************************/
select CASE
   WHEN b.number_of_pages < 150 THEN 'menys de 150 pàgines'
   WHEN b.number_of_pages >= 150 and b.number_of_pages <= 350 THEN 'entre 150 i 350 pàgines'
   WHEN b.number_of_pages > 350 and b.number_of_pages <= 500 THEN 'entre 350 i 500 pàgines'
   ELSE 'més de 500 pàgines'
   END,
       count(*)
from book b
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by CASE
             WHEN b.number_of_pages < 150 THEN 'menys de 150 pàgines'
             WHEN b.number_of_pages >= 150 and b.number_of_pages <= 350 THEN 'entre 150 i 350 pàgines'
             WHEN b.number_of_pages > 350 and b.number_of_pages <= 500 THEN 'entre 350 i 500 pàgines'
             ELSE 'més de 500 pàgines'
             END;

/***************************/
/***************************/
/****** YEAR VS YEAR  ******/
/***************************/
/***************************/
select date_part('year', date_read), count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
group by date_part('year', date_read) order by 1;
