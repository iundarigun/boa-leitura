package cat.iundarigun.boaleitura.application.port.input.statistic.impl

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticLanguageUseCase
import cat.iundarigun.boaleitura.application.port.output.StatisticPort
import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticLanguageResponse
import org.springframework.stereotype.Component

@Component
class StatisticLanguageUseCaseImpl(private val statisticPort: StatisticPort) : StatisticLanguageUseCase {

    override fun execute(request: StatisticRequest): StatisticLanguageResponse {
        val languageStatistics = statisticPort.languageStatistics(request.toStatisticsFilter())

        val totalByLanguage = languageStatistics.groupBy { it.language }
            .mapValues { (_, list) -> list.sumOf { it.count } }
        val originalPerTranslated = languageStatistics.groupBy { it.readInOriginalLanguage }
            .mapKeys { (key, _) -> if (key) "original" else "translated" }
            .mapValues { (_, list) -> list.sumOf { it.count } }
        val totalByTranslated = languageStatistics.filter { !it.readInOriginalLanguage }
            .groupBy { it.language }
            .mapValues { (_, list) -> list.sumOf { it.count } }
        val totalByOriginal = languageStatistics.filter { it.readInOriginalLanguage }
            .groupBy { it.language }
            .mapValues { (_, list) -> list.sumOf { it.count } }

        return StatisticLanguageResponse(
            totalByLanguage = totalByLanguage,
            originalPerTranslated = originalPerTranslated,
            totalByTranslated = totalByTranslated,
            totalByOriginal = totalByOriginal
        )
    }
}