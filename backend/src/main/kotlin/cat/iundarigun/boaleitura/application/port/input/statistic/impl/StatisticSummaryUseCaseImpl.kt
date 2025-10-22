package cat.iundarigun.boaleitura.application.port.input.statistic.impl

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticSummaryUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.StatisticBookResponse
import cat.iundarigun.boaleitura.domain.response.StatisticRatingResponse
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month

@Component
class StatisticSummaryUseCaseImpl(
    private val readingPort: ReadingPort,
    private val statisticPort: StatisticPort
) : StatisticSummaryUseCase {
    override fun execute(year: Int): StatisticSummaryResponse {
        val dateFrom = LocalDate.of(year, Month.JANUARY, 1)
        val dateTo = dateFrom.plusYears(1).minusDays(1)
        val readings = readingPort.find(
            dateFrom = dateFrom,
            dateTo = dateTo,
            pageRequest = PageRequest(size = 1000, order = "dateRead", directionAsc = false)
        ).content

        val response = statisticPort.summaryStatistics(dateFrom, dateTo)

        return StatisticSummaryResponse(
            year = year,
            amountOfTotalReading = response.amountOfTotalReading,
            amountOfRereading = response.amountOfRereading,
            totalPages = response.totalPages,
            averagePages = response.averagePages,
            averageRating = response.averageRating,
            bestBooks = StatisticRatingResponse(
                response.bestRating,
                readings.filter { it.myRating == response.bestRating }
                    .map { StatisticBookResponse(it.book.id, it.book.title, it.book.urlImage) }
            ),
            worseBooks = StatisticRatingResponse(response.worseRange,
                readings.filter { it.myRating == response.worseRange }
                    .map { StatisticBookResponse(it.book.id, it.book.title, it.book.urlImage) }
            )
        )
    }
}