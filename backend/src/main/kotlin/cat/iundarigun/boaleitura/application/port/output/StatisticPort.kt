package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
import cat.iundarigun.boaleitura.domain.model.StatisticSummary
import java.time.LocalDate

interface StatisticPort {
    fun summaryStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticSummary
    fun languageStatistics(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticLanguage>
}