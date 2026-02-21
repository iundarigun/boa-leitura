package cat.iundarigun.boaleitura.application.port.input.statistic.impl

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.request.StatisticsRequest
import cat.iundarigun.boaleitura.domain.response.StatisticAuthorResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month
import kotlin.math.min

@Component
class StatisticAuthorUseCaseImpl(private val statisticPort: StatisticPort) : StatisticAuthorUseCase {

    override fun execute(request: StatisticsRequest): StatisticAuthorResponse {
        val statistics = statisticPort.authorStatistics(request.toStatisticsFilter())

        return StatisticAuthorResponse(
            authorPerGender = statistics.authorGender,
            authorPerNationality = statistics.authorNationality,
            totalDistinctAuthors = statistics.authorCounts.size,
            topAuthors = statistics.authorCounts.subList(0, min(TOP_AUTHORS, statistics.authorCounts.size - 1))
                .associate { it.name to it.count },
            newAuthors = statistics.authorCounts.filter { it.newAuthor }.associate { it.name to it.count }
        )
    }

    companion object {
        const val TOP_AUTHORS = 5
    }
}