package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.StatisticAuthor
import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
import cat.iundarigun.boaleitura.domain.model.StatisticMood
import cat.iundarigun.boaleitura.domain.model.StatisticSummary
import cat.iundarigun.boaleitura.domain.model.StatisticsFilter
import java.time.LocalDate

interface StatisticPort {
    fun summaryStatistics(filter: StatisticsFilter): StatisticSummary
    fun languageStatistics(filter: StatisticsFilter): List<StatisticLanguage>
    fun authorStatistics(filter: StatisticsFilter): StatisticAuthor
    fun moodStatistics(filter: StatisticsFilter): StatisticMood
}