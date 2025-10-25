package cat.iundarigun.boaleitura.application.port.input.statistic.impl

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticMoodUseCase
import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.response.StatisticMoodResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month

@Component
class StatisticMoodUseCaseImpl(private val statisticPort: StatisticPort) : StatisticMoodUseCase {

    override fun execute(year: Int): StatisticMoodResponse {
        val dateFrom = LocalDate.of(year, Month.JANUARY, 1)
        val dateTo = dateFrom.plusYears(1).minusDays(1)
        val moodStatistics = statisticPort.moodStatistics(dateFrom, dateTo)

        val totalByFormat = moodStatistics.formatAndOrigin.groupBy { it.format }
            .mapValues { (_, list) -> list.sumOf { it.count } }
        val totalByOrigin = moodStatistics.formatAndOrigin.groupBy { it.origin }
            .mapValues { (_, list) -> list.sumOf { it.count } }

        return StatisticMoodResponse(
            totalByPageNumber = moodStatistics.totalByPageNumber,
            totalByFormat = totalByFormat,
            totalByOrigin = totalByOrigin,
        )
    }
}