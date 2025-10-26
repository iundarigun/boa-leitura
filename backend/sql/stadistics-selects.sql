

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
select a.gender, count(*)
from book b
         inner join author a on a.id = b.author_id
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by a.gender
order by 1;

/* Total */
select name,
       case when id not in (select a1.id
                            from author a1
                                     inner join book b1 on b1.author_id = a1.id
                                     inner join public.reading r1 on b1.id = r1.book_id
                            where date_part('year', date_read) < 2025)
                then true else false end as newAuthor,
       count
from (
         select a.id,
                a.name,
                count(*) as count
         from reading r
                  inner join book b on b.id = r.book_id
                  inner join author a on a.id = b.author_id
         where date_part('year', date_read) = 2025
         group by a.name, a.id) tmp
order by count desc ;


/*New authors*/
select a.name,count(*)
from book b
     inner join author a on a.id = b.author_id
     inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
  and a.id not in (select a1.id
       from author a1
        inner join book b1 on b1.author_id = a1.id
        inner join public.reading r1 on b1.id = r1.book_id
       where date_part('year', date_read) < 2025)
group by a.name;


/***************************/
/***************************/
/******  PLATFORM  *********/
/***************************/
/***************************/
select r.format, count(*)
from reading r
         inner join book b on b.id = r.book_id
         inner join author a on a.id = b.author_id
where date_part('year', date_read) = 2025
group by r.format;

select case when r.platform in ('BIBLIO', 'EBIBLIO', 'BIBLION') then 'public services'
            when r.platform in ('UNLIMITED', 'AUDIBLE', 'BOOK_BEAT') then 'subscriptions'
       else 'others' end as originType,
       count(*)
from reading r
         inner join book b on b.id = r.book_id
         inner join author a on a.id = b.author_id
where date_part('year', date_read) = 2025
group by originType;

select r.platform, count(*)
from reading r
         inner join book b on b.id = r.book_id
         inner join author a on a.id = b.author_id
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
   END as pages,
       count(*)
from book b
         inner join reading r on b.id = r.book_id
where date_part('year', date_read) = 2025
group by pages;

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
