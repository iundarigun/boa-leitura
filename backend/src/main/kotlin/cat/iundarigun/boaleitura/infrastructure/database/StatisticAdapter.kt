package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
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

        val parameterSource = MapSqlParameterSource().apply {
            addValue("dateFrom", dateFrom)
            addValue("dateTo", dateTo)
        }
        return jdbcTemplate.query(sql, parameterSource) { rs, _ ->
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

        val parameterSource = MapSqlParameterSource().apply {
            addValue("dateFrom", dateFrom)
            addValue("dateTo", dateTo)
        }

        return jdbcTemplate.query(sql, parameterSource) { rs, _ ->
            StatisticLanguage(
                rs.getBoolean("readInOriginalLanguage"),
                rs.getString("language"),
                rs.getInt("count")
            )
        }.toList()
    }
}