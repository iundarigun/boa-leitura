package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.StatisticAuthor
import cat.iundarigun.boaleitura.domain.model.StatisticLanguage
import cat.iundarigun.boaleitura.domain.model.StatisticMood
import cat.iundarigun.boaleitura.domain.model.StatisticSummary
import cat.iundarigun.boaleitura.domain.model.StatisticFilter

interface StatisticPort {
    fun summaryStatistics(filter: StatisticFilter): StatisticSummary
    fun languageStatistics(filter: StatisticFilter): List<StatisticLanguage>
    fun authorStatistics(filter: StatisticFilter): StatisticAuthor
    fun moodStatistics(filter: StatisticFilter): StatisticMood
}