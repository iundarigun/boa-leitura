package cat.iundarigun.boaleitura.application.port.input.statistic.impl

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticSummaryUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticBookResponse
import cat.iundarigun.boaleitura.domain.response.StatisticRatingResponse
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse
import org.springframework.stereotype.Component

@Component
class StatisticSummaryUseCaseImpl(
    private val readingPort: ReadingPort,
    private val statisticPort: StatisticPort
) : StatisticSummaryUseCase {

    override fun execute(request: StatisticRequest): StatisticSummaryResponse {
        val filter = request.toStatisticsFilter()
        val readings = readingPort.find(
            dateFrom = filter.dateFrom,
            dateTo = filter.dateTo,
            rereading = if (filter.excludeRereading) false else null,
            pageRequest = PageRequest(size = 1000, order = "dateRead", directionAsc = false)
        ).content

        val response = statisticPort.summaryStatistics(filter)

        return StatisticSummaryResponse(
            year = request.year,
            amountOfTotalReading = response.amountOfTotalReading,
            amountOfRereading = response.amountOfRereading,
            totalPages = response.totalPages,
            averagePages = response.averagePages,
            averageRating = response.averageRating,
            bestBooks = StatisticRatingResponse(
                response.bestRating,
                readings.filter { it.myRating == response.bestRating }
                    .map { StatisticBookResponse(it.book.id, it.book.title, it.book.urlImage) }
                    .toSet()
            ),
            worseBooks = StatisticRatingResponse(response.worseRange,
                readings.filter { it.myRating == response.worseRange }
                    .map { StatisticBookResponse(it.book.id, it.book.title, it.book.urlImage) }
                    .toSet()
            )
        )
    }
}