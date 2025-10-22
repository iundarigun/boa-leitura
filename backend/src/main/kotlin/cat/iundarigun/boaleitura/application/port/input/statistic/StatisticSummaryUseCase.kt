package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse

interface StatisticSummaryUseCase {
    fun execute(year: Int): StatisticSummaryResponse
}