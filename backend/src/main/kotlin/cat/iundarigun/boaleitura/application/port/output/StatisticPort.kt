package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.StatisticAuthor
import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
import cat.iundarigun.boaleitura.domain.model.StatisticMood
import cat.iundarigun.boaleitura.domain.model.StatisticSummary
import java.time.LocalDate

interface StatisticPort {
    fun summaryStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticSummary
    fun languageStatistics(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticLanguage>
    fun authorStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticAuthor
    fun moodStatistics(dateFrom: LocalDate, dateTo: LocalDate): StatisticMood
}