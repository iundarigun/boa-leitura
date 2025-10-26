package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.model.StatisticAuthor
import cat.iundarigun.boaleitura.domain.model.StatisticAuthorCount
import cat.iundarigun.boaleitura.domain.model.StatisticFormatAndOrigin
import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
import cat.iundarigun.boaleitura.domain.model.StatisticMood
import cat.iundarigun.boaleitura.domain.model.StatisticSummary
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StatisticAdapter(private val jdbcTemplate: NamedParameterJdbcTemplate) : StatisticPort {
    override fun summaryStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticSummary {
        val sql = """
            SELECT COUNT(distinct current_reading.id) as amountOfTotalReading,
                   SUM(book.number_of_pages) as totalPages,
                   SUM(book.number_of_pages) * 1.0 / 
                        SUM(CASE WHEN number_of_pages IS NOT NULL AND number_of_pages > 0 THEN 1 ELSE 0 END) 
                        as averagePages,
                   AVG(current_reading.my_rating) as averageRating,     
                   MAX(current_reading.my_rating) as bestRating,
                   MIN(current_reading.my_rating) as worseRating,
                   SUM((SELECT 1 FROM reading past_reading
                         WHERE past_reading.date_read < current_reading.date_read AND 
                               past_reading.book_id = current_reading.book_id)) as amountOfRereading
            FROM reading current_reading
            INNER JOIN book ON
                current_reading.book_id = book.id 
            WHERE current_reading.date_read >= :dateFrom AND 
                  current_reading.date_read <= :dateTo """

        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            StatisticSummary(
                rs.getInt("amountOfTotalReading"),
                rs.getInt("amountOfRereading"),
                rs.getInt("totalPages"),
                rs.getDouble("averagePages"),
                rs.getDouble("averageRating"),
                rs.getDouble("bestRating"),
                rs.getDouble("worseRating"),
            )
        }.single()
    }

    override fun languageStatistics(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticLanguage> {
        val sql = """
            SELECT 
                CASE WHEN b.original_language = r.language THEN true ELSE false END as readInOriginalLanguage,
                r.language as language,
                count(*) as count
            FROM book b
            INNER JOIN reading r ON 
                b.id = r.book_id
            WHERE r.date_read >= :dateFrom AND 
                  r.date_read <= :dateTo
            GROUP BY readInOriginalLanguage, r.language """

        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            StatisticLanguage(
                rs.getBoolean("readInOriginalLanguage"),
                rs.getString("language"),
                rs.getInt("count")
            )
        }.toList()
    }

    override fun authorStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticAuthor {
        return StatisticAuthor(
            authorGender = retrieveAuthorGender(dateFrom, dateTo),
            authorCounts = retrieveAuthorCount(dateFrom, dateTo)
        )
    }

    override fun moodStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticMood {
        return StatisticMood(
            totalByPageNumber = retrieveByBookSize(dateFrom, dateTo),
            formatAndOrigin = retrieveFormatAndOrigin(dateFrom, dateTo)
        )
    }

    private fun retrieveAuthorGender(dateFrom: LocalDate, dateTo: LocalDate): Map<String, Int> {
        val sql = """
            SELECT a.gender as gender, count(*) as count FROM reading r
            INNER JOIN book b ON 
                b.id = r.book_id
            INNER JOIN author a ON 
                a.id = b.author_id
            WHERE r.date_read >= :dateFrom AND 
                  r.date_read <= :dateTo
            GROUP BY a.gender
            """
        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            rs.getString("gender") to rs.getInt("count")
        }.toMap()
    }

    private fun retrieveAuthorCount(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticAuthorCount> {
        val sql = """
           SELECT tmp.name as name,
                  CASE WHEN tmp.id NOT IN 
                        (SELECT a1.id FROM reading r1
                         INNER JOIN book b1 ON
                            b1.id = r1.book_id
                         INNER JOIN author a1 ON
                            b1.author_id = a1.id
                        WHERE r1.date_read < :dateFrom) THEN true 
                     ELSE false end as newAuthor,
                  tmp.count as count
           FROM (SELECT a.id, a.name, count(*) as count
                 FROM reading r
                 INNER JOIN book b ON 
                    b.id = r.book_id
                 INNER JOIN author a ON 
                    a.id = b.author_id
                 WHERE r.date_read >= :dateFrom AND 
                       r.date_read <= :dateTo
           GROUP BY a.name, a.id) tmp
           ORDER BY count DESC"""
        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            StatisticAuthorCount(
                rs.getString("name"),
                rs.getBoolean("newAuthor"),
                rs.getInt("count")
            )
        }.toList()
    }

    private fun retrieveFormatAndOrigin(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticFormatAndOrigin> {
        val sql = """
            SELECT r.format as format,
                   CASE
                    WHEN r.platform IN ('BIBLIO', 'EBIBLIO', 'BIBLION') THEN 'public services'
                    WHEN r.platform IN ('UNLIMITED', 'AUDIBLE', 'BOOK_BEAT') THEN 'subscriptions'
                    ELSE 'others'
                    END AS origin,
                   count(*) as count
            FROM reading r
            INNER JOIN book b ON 
                b.id = r.book_id
            INNER JOIN author a ON 
                a.id = b.author_id
            WHERE r.date_read >= :dateFrom AND 
                  r.date_read <= :dateTo
            GROUP BY format, origin """

        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            StatisticFormatAndOrigin(
                rs.getString("format"),
                rs.getString("origin"),
                rs.getInt("count")
            )
        }.toList()
    }

    private fun retrieveByBookSize(dateFrom: LocalDate, dateTo: LocalDate): Map<String, Int> {
        val sql = """
            SELECT CASE
                       WHEN b.number_of_pages < ${NUMBER_OF_PAGES[0]} THEN 'less than ${NUMBER_OF_PAGES[0]} pages'
                       WHEN b.number_of_pages >= ${NUMBER_OF_PAGES[0]} and b.number_of_pages <= ${NUMBER_OF_PAGES[1]} THEN 'between ${NUMBER_OF_PAGES[0]} and ${NUMBER_OF_PAGES[1]} pages'
                       WHEN b.number_of_pages > ${NUMBER_OF_PAGES[1]} and b.number_of_pages <= ${NUMBER_OF_PAGES[2]} THEN 'between ${NUMBER_OF_PAGES[1]} and ${NUMBER_OF_PAGES[2]} pages'
                       ELSE 'more than ${NUMBER_OF_PAGES[2]}'
                   END as pages,
                  count(*) as count
            FROM reading r
            INNER JOIN book b ON 
                b.id = r.book_id
            WHERE r.date_read >= :dateFrom AND 
                  r.date_read <= :dateTo
            GROUP BY pages """
        return jdbcTemplate.query(sql, parameterSource(dateFrom, dateTo)) { rs, _ ->
            rs.getString("pages") to rs.getInt("count")
        }.toMap()
    }

    private fun parameterSource(dateFrom: LocalDate, dateTo: LocalDate): MapSqlParameterSource =
        MapSqlParameterSource().apply {
            addValue("dateFrom", dateFrom)
            addValue("dateTo", dateTo)
        }

    companion object {
        val NUMBER_OF_PAGES = listOf(150, 350, 500)
    }
}